package cl.uchile.dcc.citric
package controller.states

import controller.GameController
import model.unit.player.PlayerCharacter

import exceptions.InvalidTransitionException
import org.junit.Assert.assertThrows

class PlayerTurnStateTest extends munit.FunSuite{

  private var controller: GameController = _
  private var state: PlayerTurnState = _
  private var player: PlayerCharacter = _

  override def beforeEach(context: BeforeEach): Unit = {

    controller = new GameController
    state = new PlayerTurnState
    player = new PlayerCharacter("Joel", 1,1,1,1)

  }

  test("A state should be able to add a controller") {
    state.controller = controller
    assertEquals(state.controller, controller)
  }

  test("A state should transition correctly") {
    state.controller = controller
    controller.selected = player
    state.rollDice()
    assertEquals(controller.state.isChapter, false)
    assertEquals(controller.state.isCombat, false)
    assertEquals(controller.state.isEnd, false)
    assertEquals(controller.state.isLanding, false)
    assertEquals(controller.state.isMoving, true)
    assertEquals(controller.state.isTurn, false)
    assertEquals(controller.state.isRecovery, false)
    assertEquals(controller.state.isStart, false)
    assertEquals(controller.state.isWait, false)
  }

  test("Invalid transitions Test") {
    assertThrows(classOf[InvalidTransitionException], () => state.startGame())
    assertThrows(classOf[InvalidTransitionException], () => state.doEffect())
    assertThrows(classOf[InvalidTransitionException], () => state.newChapter())
    assertThrows(classOf[InvalidTransitionException], () => state.playTurn())
    assertThrows(classOf[InvalidTransitionException], () => state.stopMovement())
    assertThrows(classOf[InvalidTransitionException], () => state.endCombat())
    assertThrows(classOf[InvalidTransitionException], () => state.normaSix())
    assertThrows(classOf[InvalidTransitionException], () => state.knockedOut())
    assertThrows(classOf[InvalidTransitionException], () => state.choosePath(1))
    assertThrows(classOf[InvalidTransitionException], () => state.outOfMovements())
    assertThrows(classOf[InvalidTransitionException], () => state.attack(1))
    assertThrows(classOf[InvalidTransitionException], () => state.recoveryRoll())
    assertThrows(classOf[InvalidTransitionException], () => state.respond(1))
  }
}

