package cl.uchile.dcc.citric
package controller.states

import controller.GameController
import model.unit.player.PlayerCharacter

import exceptions.InvalidTransitionException
import org.junit.Assert.assertThrows

class RecoveryStateTest extends munit.FunSuite{

  private var controller: GameController = _
  private var state: RecoveryState = _
  private var player: PlayerCharacter = _

  override def beforeEach(context: BeforeEach): Unit = {

    controller = new GameController
    state = new RecoveryState
    player = new PlayerCharacter("Janpi", 1,1,1,1)
    state.controller = controller
    player.takeDamage(1)

  }

  test("A state should be able to add a controller") {
    assertEquals(state.controller, controller)
  }

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
    assertThrows(classOf[InvalidTransitionException], () => state.respond(1))
  }
}
