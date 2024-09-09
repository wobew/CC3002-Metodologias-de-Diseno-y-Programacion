package cl.uchile.dcc.citric
package controller.states

import controller.GameController

import cl.uchile.dcc.citric.exceptions.InvalidTransitionException
import org.junit.Assert.assertThrows


class StartStateTest extends munit.FunSuite{

  private var controller: GameController = _
  private var state: StartState = _

  override def beforeEach(context: BeforeEach): Unit = {

    controller = new GameController
    state = new StartState

  }

  test("A state should be able to add a controller") {
    state.controller = controller
    assertEquals(state.controller, controller)
    assertEquals(state.isStart, true)
  }

  test("A state should transition correctly") {
    state.controller = controller
    state.startGame()
    assertEquals(controller.state.isChapter, true)
    assertEquals(controller.state.isCombat, false)
    assertEquals(controller.state.isEnd, false)
    assertEquals(controller.state.isLanding, false)
    assertEquals(controller.state.isMoving, false)
    assertEquals(controller.state.isTurn, false)
    assertEquals(controller.state.isRecovery, false)
    assertEquals(controller.state.isStart, false)
    assertEquals(controller.state.isWait, false)
  }

  test("Invalid transitions Test") {
    assertThrows(classOf[InvalidTransitionException], () => state.rollDice())
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

  /*
  test("Invalid transitions Test") {
    assertThrows(classOf[InvalidTransitionException], () => state.startGame())
    assertThrows(classOf[InvalidTransitionException], () => state.rollDice())
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
   */

}
