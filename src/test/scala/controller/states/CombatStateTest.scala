package cl.uchile.dcc.citric
package controller.states

import controller.GameController
import model.unit.player.PlayerCharacter
import model.panel.{HomePanel, NeutralPanel}
import exceptions.{InvalidInputException, InvalidTransitionException}

import org.junit.Assert.assertThrows


class CombatStateTest extends munit.FunSuite{

  private var controller: GameController = _
  private var state: CombatState = _
  private var player: PlayerCharacter = _
  private var player2: PlayerCharacter = _
  private var player3: PlayerCharacter = _
  private var panel: NeutralPanel = _

  override def beforeEach(context: BeforeEach): Unit = {

    controller = new GameController
    state = new CombatState
    player = new PlayerCharacter("Caldecrack", 1, 100, 1, 1)
    player2 = new PlayerCharacter("Este Banko", 1, 1, 1, 1)
    player3 = new PlayerCharacter("Ale", 1, 1, 1, 1)
    panel = new NeutralPanel

    panel.addCharacter(player)
    panel.addCharacter(player2)
    panel.addCharacter(player3)
    state.controller = controller
    controller.selected = player
    controller.panel = panel

  }

  test("A state should be able to add a controller") {
    assertEquals(state.controller, controller)
  }

  test("A player can choose to fight between multiple characters - test 1") {
    state.attack(1)
    assertEquals(controller.target, player2)
    assertEquals(controller.state.isChapter, false)
    assertEquals(controller.state.isCombat, false)
    assertEquals(controller.state.isEnd, false)
    assertEquals(controller.state.isLanding, false)
    assertEquals(controller.state.isMoving, false)
    assertEquals(controller.state.isTurn, false)
    assertEquals(controller.state.isRecovery, false)
    assertEquals(controller.state.isStart, false)
    assertEquals(controller.state.isWait, true)
  }

  test("A player can choose to fight between multiple panels - test 2") {
    state.attack(2)
    assertEquals(controller.target, player3)
    assertEquals(controller.state.isChapter, false)
    assertEquals(controller.state.isCombat, false)
    assertEquals(controller.state.isEnd, false)
    assertEquals(controller.state.isLanding, false)
    assertEquals(controller.state.isMoving, false)
    assertEquals(controller.state.isTurn, false)
    assertEquals(controller.state.isRecovery, false)
    assertEquals(controller.state.isStart, false)
    assertEquals(controller.state.isWait, true)

  }


  test("A player can choose not to combat") {
    state.attack(3)
    assertEquals(controller.state.isChapter, false)
    assertEquals(controller.state.isCombat, false)
    assertEquals(controller.state.isEnd, false)
    assertEquals(controller.state.isLanding, true)
    assertEquals(controller.state.isMoving, false)
    assertEquals(controller.state.isTurn, false)
    assertEquals(controller.state.isRecovery, false)
    assertEquals(controller.state.isStart, false)
    assertEquals(controller.state.isWait, false)
  }

  test("Invalid input test") {
    assertThrows(classOf[InvalidInputException], () => state.attack(4))
  }

  test("Invalid transitions Test") {
    assertThrows(classOf[InvalidTransitionException], () => state.startGame())
    assertThrows(classOf[InvalidTransitionException], () => state.rollDice())
    assertThrows(classOf[InvalidTransitionException], () => state.doEffect())
    assertThrows(classOf[InvalidTransitionException], () => state.newChapter())
    assertThrows(classOf[InvalidTransitionException], () => state.playTurn())
    assertThrows(classOf[InvalidTransitionException], () => state.stopMovement())
    assertThrows(classOf[InvalidTransitionException], () => state.normaSix())
    assertThrows(classOf[InvalidTransitionException], () => state.knockedOut())
    assertThrows(classOf[InvalidTransitionException], () => state.choosePath(1))
    assertThrows(classOf[InvalidTransitionException], () => state.outOfMovements())
    assertThrows(classOf[InvalidTransitionException], () => state.recoveryRoll())
    assertThrows(classOf[InvalidTransitionException], () => state.respond(1))
  }

}