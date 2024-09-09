package cl.uchile.dcc.citric
package controller

import model.unit.player.PlayerCharacter
import model.unit.wild.WildUnit
import controller.states.{LandingPanelState, WaitState}

import scala.collection.mutable.ArrayBuffer

class GameControllerTest extends munit.FunSuite  {


  private var game: GameController = _
  private val seq: Seq[(String, Int, Int, Int, Int)] = Seq(
    ("Joel", 200, 200, 200, 200),
    ("Vicente", 10, 5, 5, 5),
    ("Bigel", 10, 5, 5, 5),
    ("Slater", 10, 5, 5, 5)
  )

  override def beforeEach(context: BeforeEach): Unit = {

    game = new GameController()


  }

  test("A Game Controller should be created with its correct parameters") {
    assertEquals(game.players, ArrayBuffer.empty[PlayerCharacter].toList)
    assertEquals(game.roll, 0)
    assertEquals(game.chapter, 0)
  }

  test("A Game Controller can start a game with some players") {
    game.startGame(seq)
    assertEquals(game.players.length, 4)
  }

  test("A Game Controller can select and target a player"){
    game.startGame(seq)
    assertEquals(game.selected.name, "Joel")
    game.selected = game.players(2)
    game.target = game.players(1)
    assertEquals(game.selected.name, "Bigel")
    assertEquals(game.target.name, "Vicente")
  }

  test("A Controller can change states") {
    game.changeState(new WaitState)
    assert(game.state.isInstanceOf[WaitState])
  }

  test("A Controller can use the function to select the next player in order") {
    game.startGame(seq)
    assertEquals(game.selected.name, "Joel")
    game.selectNextPlayer()
    assertEquals(game.selected.name, "Vicente")
    game.selectNextPlayer()
    assertEquals(game.selected.name, "Bigel")
    game.selectNextPlayer()
    assertEquals(game.selected.name, "Slater")
    game.selectNextPlayer()
    assertEquals(game.selected.name, "Joel")
  }

  test("A Controller switch between selected panels") {
    game.startGame(seq)
    val panel = game.panel
    game.panel = game.getPanel(game.players(2))
    assertNotEquals(game.panel, panel)
  }

  test("A Controller can advance in chapter") {
    game.chapter = 1
    assertEquals(game.chapter, 1)
  }

  test("A Controller can make a player to roll a dice") {
    game.startGame(seq)
    game.playerRoll()
    assertNotEquals(game.roll, 0)
  }

  test("A Controller can give stars for the players") {
    game.startGame(seq)
    for(player <- game.players) {
      assertEquals(player.stars, 0)
    }
    game.stars()
    for (player <- game.players) {
      assertEquals(player.stars, 1)
    }
  }

  test("A Controller can apply a panel effect") {
    game.startGame(seq)
    game.selected.removeHp(8)
    assertEquals(game.selected.hp, 192)
    game.panelApply()
    assertEquals(game.selected.hp, 193)
  }

  test("A Controller can do an attack between two player characters") {
    game.startGame(seq)
    game.target = game.players(2)
    game.doAttack("")
    assertNotEquals(game.players(2).hp, 10)
  }

  test("A Controller can move a character between panels") {
    game.startGame(seq)
    val panel = game.panel
    assert(panel.characters.contains(game.selected))
    game.moveTo(1)
    assertNotEquals(game.getPanel(game.selected), panel)
  }

  test("ChapterState newChapter transition test") {
    game.startGame(seq)
    game.state.startGame()
    assertEquals(game.state.isChapter, true)
    game.state.newChapter()
    assertEquals(game.chapter, 1)
    assertEquals(game.selected.name, "Joel")
    assert(game.selected.stars > 0)
    assertEquals(game.state.isChapter, true)
    assertEquals(game.state.isCombat, false)
    assertEquals(game.state.isEnd, false)
    assertEquals(game.state.isLanding, false)
    assertEquals(game.state.isMoving, false)
    assertEquals(game.state.isTurn, false)
    assertEquals(game.state.isRecovery, false)
    assertEquals(game.state.isStart, false)
    assertEquals(game.state.isWait, false)
  }

  test("ChapterState playTurn transition test") {
    game.startGame(seq)
    game.state.startGame()
    assertEquals(game.state.isChapter, true)
    game.state.playTurn()
    assertEquals(game.state.isChapter, false)
    assertEquals(game.state.isCombat, false)
    assertEquals(game.state.isEnd, false)
    assertEquals(game.state.isLanding, false)
    assertEquals(game.state.isMoving, false)
    assertEquals(game.state.isTurn, true)
    assertEquals(game.state.isRecovery, false)
    assertEquals(game.state.isStart, false)
    assertEquals(game.state.isWait, false)
  }

  test("ChapterState knockedOut transition test") {
    game.startGame(seq)
    game.state.startGame()
    assertEquals(game.state.isChapter, true)
    game.selected.takeDamage(1000)
    game.state.knockedOut()
    assertEquals(game.state.isChapter, false)
    assertEquals(game.state.isCombat, false)
    assertEquals(game.state.isEnd, false)
    assertEquals(game.state.isLanding, false)
    assertEquals(game.state.isMoving, false)
    assertEquals(game.state.isTurn, false)
    assertEquals(game.state.isRecovery, true)
    assertEquals(game.state.isStart, false)
    assertEquals(game.state.isWait, false)
  }

  test("Norma 6 leads to end game test") {
    game.startGame(seq)
    game.changeState(new LandingPanelState)
    game.selected.addStars(1000)
    game.state.doEffect("")
    assertEquals(game.selected.norma, 6)
    assertEquals(game.state.isChapter, false)
    assertEquals(game.state.isCombat, false)
    assertEquals(game.state.isEnd, true)
    assertEquals(game.state.isLanding, false)
    assertEquals(game.state.isMoving, false)
    assertEquals(game.state.isTurn, false)
    assertEquals(game.state.isRecovery, false)
    assertEquals(game.state.isStart, false)
    assertEquals(game.state.isWait, false)
  }

}