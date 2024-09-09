package cl.uchile.dcc.citric
package model.unit.player

import model.norma.Norma
import model.unit.wild.WildUnit
import model.unit.{AbstractUnit, GameUnit}
import exceptions.InvalidInputException
import controller.{Observer, Subject}

import scala.util.Random

/** The `PlayerCharacter` class represents a character or avatar in the game, encapsulating
 * several attributes such as health points, attack strength, defense capability,
 * and evasion skills. Each player has a unique name, and throughout the game,
 * players can collect stars, roll dice, and progress in levels known as 'norma'.
 * This class not only maintains the state of a player but also provides methods
 * to modify and interact with these attributes.
 *
 * For instance, players can:
 *
 * - Increase or decrease their star count.
 *
 * - Roll a dice, a common action in many board games.
 *
 * - Advance their norma level.
 *
 * Furthermore, the `Player` class has a utility for generating random numbers,
 * which is primarily used for simulating dice rolls. By default, this utility is
 * an instance of the `Random` class but can be replaced if different random
 * generation behaviors are desired.
 *
 * @param _name The name of the player. This is an identifier and should be unique.
 * @param _maxHp The maximum health points a player can have. It represents the player's endurance.
 * @param _attack The player's capability to deal damage to opponents.
 * @param _defense The player's capability to resist or mitigate damage from opponents.
 * @param _evasion The player's skill to completely avoid certain attacks.
 * @param randomNumberGenerator A utility to generate random numbers. Defaults to a new `Random`
 *                              instance.
 *
 * @author [[https://github.com/danielRamirezL/ Daniel Ramírez L.]]
 * @author [[https://github.com/joelriquelme/ Joel Riquelme P.]]
 * @author [[https://github.com/r8vnhill/ Ignacio Slater M.]]
 * @author [[https://github.com/Seivier/ Vicente González B.]]
 * @author [[https://github.com/wobew/ Roberto Rivera C.]]
 */
  class PlayerCharacter(private val _name: String,
                        _maxHp: Int,
                        _attack: Int,
                        _defense: Int,
                        _evasion: Int,
                        randomNumberGenerator: Random = new Random())
  extends AbstractUnit(_maxHp, _attack, _defense, _evasion,
    randomNumberGenerator) with PlayerUnit with Subject {

  private var observers: List[Observer] = List.empty


  /**
   * Adds an observer to the list of observers. This is part of the Observer pattern,
   * allowing for changes in the player character's state to be observed by other components.
   *
   * @param observer The observer to be added.
   */
  def addObserver(observer: Observer): Unit = {
    observers = observer :: observers
  }


  /**
   * Notifies all observers about a change in the state of this player character.
   * Specifically, it will be used to notify about the winning condition: reaching norma 6 level
   */
  def notifyObservers(): Unit = {
    for(observer <- observers) {
      observer.update(this)
    }
  }

  /**
   * The name of the player character. Unique identifier used in the game.
   */
  def name: String = _name

  /**
   * The current norma level of the player character. Norma levels are milestones that players aim to achieve in order to win the game.
   */
  private var _norma = 1

  /**
   * A getter method for norma
   *  @return The current Norma level of the player unit.
   */
  def norma: Int = _norma

  /**
   * A setter method for the norma
   * @param newNorma the new norma level
   */
  protected def norma_=(newNorma: Int): Unit = {
    _norma = newNorma
    if (_norma < 0) _norma = 0
  }

  /**
   * Calls the setter method to add a norma level, it notifies the observers if needed
   *
   * @param plus The HP to be added
   */
  def addNorma(plus: Int): Unit = {
    norma += plus
    if(_norma == 6) notifyObservers()
  }

  /**
   * Norma object for the player character, determining the conditions for level progression.
   */
  private var _normaClass: Norma = _

  /**
   * A getter method for the Norma Class
   *  @return The Norma class instance tied to this player unit.
   */
  def normaClass: Norma = _normaClass

  /**
   * A setter method for the Norma Class
   * @param newNormaClass the new Norma Class of the player
   */
  def normaClass_=(newNormaClass: Norma): Unit = {
    _normaClass = newNormaClass
  }

  /**
   * The number of victories this player character has. Victories contribute to
   * the conditions for norma level progression.
   */
  private var _victories = 0

  /**
   * A getter method for the vicories of the player
   *  @return The number of victories of the player unit.
   */
  def victories: Int = _victories

  /**
   * A setter method for the victories of the player
   * @param newVictories the new number of victories
   */
  protected def victories_=(newVictories: Int): Unit = {
    _victories = math.max(0, newVictories)
  }

  /**
   * Calls the setter method to add a quantity of victories
   *
   * @param plus The victories to be added
   */
  def addVictories(plus: Int): Unit = {
    victories += plus
  }

  /**
   * Calls the setter method to remove a quantity of victories
   *
   * @param minus The victories to be removed
   */
  def removeVictories(minus: Int): Unit = {
    victories -= minus
  }


  /**
   * Indicates whether the player character is currently on their home panel,
   * which has implications for certain game mechanics.
   */
  private var _inHome: Boolean = true

  /**
   * A getter method for the player boolean in home
   *  @return True if the player unit is on its home panel, false otherwise.
   */
  def inHome: Boolean = _inHome

  /**
   * A setter method for the player boolean in home
   * @param newInHome the new in Home boolean for the player
   */
  def inHome_=(newInHome: Boolean): Unit = {
    _inHome = newInHome
  }

  /**
   * Indicates whether the player character has been knocked out (KO'd).
   * A KO'd player character may have limited actions until they are revived.
   */
  private var _KO: Boolean = false

  /**
   *
   * A getter method for the player boolean KO
   */
  def KO: Boolean = _KO

  /**
   * A setter method for the boolean value KO of the player
   * @param newKO the new boolean
   */
  def KO_=(newKO: Boolean): Unit = {
    _KO = newKO
  }

  /**
   * Initiates an attack against another game unit, provided the player character is not KO'd.
   * The attack's effectiveness is determined by the player's attack attribute plus a dice roll.
   *
   * @param enemy The game unit being attacked.
   */
  def doAttack(enemy: GameUnit, input: String = ""): Unit = {
    if (KO) return
    val atk = attack + rollDice()
    enemy.attackPlayer(this, atk, input)
  }


  /**
   * Responds to an attack by another player character. If this player is knocked out (KO),
   * the attack is disregarded. Otherwise, the player chooses to defend or evade based on
   * the provided input, which can be simulated or determined by the game's logic.
   *
   * If a defense is chosen, the player mitigates the incoming attack based on their defense
   * attribute and a dice roll. If an evasion is chosen, the player attempts to avoid the
   * attack entirely, succeeding based on their evasion attribute and a dice roll.
   *
   * Should the player's HP drop to 0 as a result of the attack, they are considered knocked out,
   * and the attacker is awarded victory points. Additionally, the attacker gains half of the
   * stars that the defeated player had, rounding down.
   *
   * @param enemyPlayer The player character initiating the attack.
   * @param atk         The total attack value calculated for the attacking player.
   * @param input       Optional input to simulate the defense or evasion choice. If blank, the choice is made randomly.
   */
  def attackPlayer(enemyPlayer: PlayerCharacter, atk: Int, input: String = ""): Unit = {
    if (KO) return
    val tactic = simulateInput(input)
    if (tactic == "defend") defend(atk) else if (tactic == "evade") evade(atk)
    if (hp == 0) {
      enemyPlayer.victories += 2
      val halfStars = stars / 2
      stars -= halfStars
      enemyPlayer.stars += halfStars
    }
  }

  /**
   * Responds to an attack by a wild unit. If this player is knocked out (KO),
   * the attack is disregarded. Otherwise, the player chooses to evade or defend based on
   * the provided input, which can be simulated or determined by the game's logic.
   *
   * If an evasion is chosen and is successful, the player avoids the attack entirely.
   * If a defense is chosen or the evasion fails, the player mitigates or takes full damage.
   *
   * If the player's HP drops to 0 as a result of the attack, they lose half their stars,
   * rounded down, which are then given to the wild unit.
   *
   * @param enemyWild The wild unit initiating the attack.
   * @param atk       The total attack value calculated for the wild unit.
   * @param input     Optional input to simulate the evasion or defense choice. If blank, the choice is made randomly.
   */
  def attackWild(enemyWild: WildUnit, atk: Int, input: String = ""): Unit = {
    if (KO) return
    val tactic = simulateInput(input)
    if (tactic == "evade") evade(atk) else if (tactic == "defend") defend(atk)
    if (hp == 0) {
      val halfStars = stars / 2
      stars -= halfStars
      enemyWild.addStars(halfStars)
    }
  }

  /**
   * Applies damage to the player and updates their knocked out (KO) status if the HP drops to 0.
   * Damage taken is subtracted from the current HP, and HP cannot be reduced below 0.
   *
   * @param dmg The amount of damage to be applied.
   */
  def takeDamage(dmg: Int): Unit = {
    hp = math.max(hp - dmg, 0)
    if (hp == 0) KO = true
  }

  /**
   * Processes or simulates the player's decision to defend or evade during an attack.
   * If the input is empty, a random decision is made. If the input is invalid, an exception is thrown.
   * This method allows for controlled testing or AI decision-making in the game.
   *
   * @param input The decision input, can be "defend", "evade", or empty for a random choice.
   * @return The chosen tactic as a string ("defend" or "evade").
   * @throws InvalidInputException if the input is neither "defend", "evade", nor empty.
   */
  def simulateInput(input: String): String = {
    if (input != "defend" && input != "evade" && input != "") {
      throw new InvalidInputException(input)
    }
    if (input == "") {
      if (rollDice() % 2 == 0) "defend" else "evade"
    } else {
      input
    }
  }

}