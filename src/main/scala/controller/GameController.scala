package cl.uchile.dcc.citric
package controller

import controller.states.{GameState, StartState}
import model.unit.player.PlayerCharacter
import model.unit.wild.WildUnit
import model.panel.{BonusPanel, DropPanel, EncounterPanel, HomePanel, NeutralPanel, Panel}
import model.norma.Norma1

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * The `GameController` class manages the overall game state and logic. It controls the game's flow, player actions,
 * and interactions with panels and units. It also serves as an observer and respond to player actions.
 */
class GameController extends Observer {

  /**
   * The current state of the game
   */
  private var _state: GameState = _

  /**
   * A buffer containing all the players of the game
   */
  private val _players: ArrayBuffer[PlayerCharacter] = ArrayBuffer.empty[PlayerCharacter]

  /**
   * A map of all the players and their respective panel, serves as a memory to transition correctly
   * between player's turn
   */
  private val _map: mutable.Map[PlayerCharacter, Panel] = mutable.Map.empty

  /**
   * The actual player selected by the controller, i.e. the player who is playing their turn
   */
  private var _selected: Option[PlayerCharacter] = None

  /**
   * The player targeted by the selected player to be attacked in combat
   */
  private var _target: Option[PlayerCharacter] = None

  /**
   * The current panel of the selected player
   */
  private var _panel: Option[Panel] = None

  /**
   * Serves as a count of the turns, used intelligently with the module operation to change
   * the selected player
   */
  private var count: Int = 0

  /**
   * A variable to store the latest player dice roll
   */
  private var _roll: Int = 0

  /**
   * The count of the actual chapter
   */
  private var _chapter: Int = 0

  /**
   * A map containing the recovery requirement needed by each player to restore
   * some Hp and continue playing
   */
  private var _recovery: mutable.Map[PlayerCharacter, Int] = mutable.Map.empty

  /**
   * Returns the recovery map, which tracks the recovery status of players.
   */
  def recovery: mutable.Map[PlayerCharacter, Int] = _recovery

  /**
   * Sets the recovery map to the provided value.
   *
   * @param rec The new recovery map.
   */
  def recovery_=(rec:mutable.Map[PlayerCharacter, Int]): Unit = _recovery = rec

  /**
   * Returns the list of player characters in the game.
   */
  def players: List[PlayerCharacter] = _players.toList

  /**
   * Returns the currently selected player character.
   */
  def selected: PlayerCharacter = {
    if (_selected.isDefined) {
      _selected.get
    } else {
      throw new AssertionError("Player not defined")
    }
  }

  /**
   * Returns the target player character for actions.
   */
  def target: PlayerCharacter = {
    if (_target.isDefined) {
      _target.get
    } else {
      throw new AssertionError("Enemy not defined")
    }
  }

  /**
   * Returns the current panel where the player character is located.
   */
  def panel: Panel = {
    if (_panel.isDefined) {
      _panel.get
    } else {
      throw new AssertionError("Panel not defined")
    }
  }

  /**
   * Sets the current panel where the player character is located.
   *
   * @param panel The new panel.
   */
  def panel_=(panel: Panel): Unit = _panel = Some(panel)

  /**
   * Returns the panel associated with a specific player character.
   *
   * @param player The player character.
   * @return The panel associated with the player character.
   * @throws NoSuchElementException if the panel is not found for the player.
   */
  def getPanel(player: PlayerCharacter): Panel = {
    _map.getOrElse(player, throw new NoSuchElementException("Panel not found for player"))
  }

  /**
   * Sets the currently selected player character.
   *
   * @param player The player character to select.
   */
  def selected_=(player: PlayerCharacter): Unit = _selected = Some(player)

  /**
   * Sets the target player character for combat.
   *
   * @param player The target player character.
   */
  def target_=(player: PlayerCharacter): Unit = _target = Some(player)

  /**
   * Returns the result of the most recent dice roll.
   */
  def roll: Int = _roll

  /**
   * Returns the current chapter of the game.
   */
  def chapter: Int = _chapter

  /**
   * Sets the current chapter of the game.
   *
   * @param chapter The new chapter.
   */
  def chapter_=(chapter: Int): Unit = _chapter = chapter

  /**
   * Returns the current game state.
   */
  def state: GameState = _state

  /**
   * Changes the current game state to the provided state.
   *
   * @param st The new game state.
   */
  def changeState(st: GameState): Unit = {
    _state = st
    st.controller = this
  }

  /**
   * The method of the controller as observer to be notified when a player reaches the winning condition of the
   * game and so can transit to the End Game State
   *
   * @param observable The `PlayerCharacter` instance that has reached norma 6
   */
  def update(observable: PlayerCharacter): Unit = _state.normaSix()

  /**
   * A prompt start to simulate a basic board and 4 player games used in testing, it was not required in the
   * assignment to do a multiple board game and the 4 players condition was required.
   *
   * @param playerCharacters the sequence of player's characters of the game and their statistics
   */
  def startGame(playerCharacters: Seq[(String,Int, Int, Int, Int)]): Unit = {

    changeState(new StartState())

    for((name, maxHp, attack, defense, evasion) <- playerCharacters) {
      val character = new PlayerCharacter(name, maxHp, attack, defense, evasion)
      new Norma1(character)
      character.addObserver(this)
      _players += character
    }

    println("Constructing the board...")
    val panel1: HomePanel = new HomePanel(_players(0))
    val panel2: Panel = new HomePanel(_players(1))
    val panel3: Panel = new HomePanel(_players(2))
    val panel4: Panel = new HomePanel(_players(3))

    val panel5: Panel = new NeutralPanel
    val panel6: Panel = new BonusPanel
    val panel7: Panel = new DropPanel
    val panel8: Panel = new EncounterPanel

    panel1.addPanel(panel2)
    panel2.addPanel(panel3)
    panel3.addPanel(panel4)
    panel4.addPanel(panel5)

    panel5.addPanel(panel6)
    panel5.addPanel(panel7)
    panel5.addPanel(panel8)

    panel8.addPanel(panel1)

    _map += (_players(0) -> panel1)
    _map += (_players(1) -> panel2)
    _map += (_players(2) -> panel3)
    _map += (_players(3) -> panel4)

    _recovery += (_players(0) -> 6)
    _recovery += (_players(1) -> 6)
    _recovery += (_players(2) -> 6)
    _recovery += (_players(3) -> 6)

    selected = panel1.owner
    panel = panel1

    println("WELCOME 2 GAMING")
  }

  /**
   * A function to change the selected player of the controller for the next in row
   */
  def selectNextPlayer(): Unit = {
    count += 1
    _selected = Some(players(count % players.length))
    _panel = Some(_map(selected))
  }

  /**
   * A method to do a player dice roll and store its result
   */
  def playerRoll(): Unit = {
    if (_selected.isDefined) {
      _roll = _selected.get.rollDice()
    } else {
      throw new AssertionError("Player not defined")
    }
  }

  /**
   * The controller's immediate behavior when a player is moving between panels, showing them all possible
   * options given their current panel
   */
  def movingPrompt(): Unit = {
    if(_roll > 0) {
      println(s"Player moving, $roll panels remaining")
      println("Choose a path to move")
      _roll -= 1
      for (i <- panel.nextPanels.indices) {
        println(s"${i + 1}) ${panel.nextPanels(i).name}")
      }
      if(selected.inHome) println(s"${panel.nextPanels.length + 1}) Do you wanna stay home?")
    }
  }

  /**
   * the immediate behavior of the controller when a player finishes his move and can choose to fight one of
   * the available players or not
   */
  def combatPrompt(): Unit = {
    if(panel.characters.length > 1) {
      println(s"Choose a rival")
      panel.removeCharacter(selected)
      for (i <- panel.characters.indices) {
        println(s"${i + 1}) ${panel.characters(i).name}")
      }
      println(s"${panel.characters.length + 1}) No Combat")
    }
  }

  /**
   * The method to be called when the controller needs to move the player between panels, it updates the
   * current panel in the panel variable and in the map
   *
   * @param to the option chosen by the player to move to
   */
  def moveTo(to: Int): Unit = {
    panel.removeCharacter(selected)
    panel = panel.nextPanels(to - 1)
    panel.addCharacter(selected)
    _map(selected) = panel
  }

  /**
   * The method of the controller to be called when it needs to simulate a combat between two players
   *
   * @param input the simulated response input of the attacked player
   */
  def doAttack(input: String = ""): Unit = {
    selected.doAttack(target, input)
  }

  /**
   * The method to by called when simulating a combat with a Wild Unit in an Encounter Panel
   *
   * @param wild the wild unit which is Fighting
   * @param input the response of the player to the possible attack of the wild unit
   */
  def doAttackWild(wild: WildUnit, input: String): Unit = {
    selected.doAttack(wild)
    wild.doAttack(selected, input)
  }

  /**
   * The method by which the controller applies a panel effect upon the selected player
   *
   * @param input the response of the player to the Wild Unit attack in case it is in an
   *              Encounter panel
   */
  def panelApply(input: String = ""): Unit = {
    panel.apply(selected, Option(this), input)
  }

  /**
   * The method by which the controller gives the players some stars when a new chapter is
   * started, just awes:Ome
   */
  def stars(): Unit = {
    for(player <- players) player.addStars((chapter/5) + 1)
  }

}
