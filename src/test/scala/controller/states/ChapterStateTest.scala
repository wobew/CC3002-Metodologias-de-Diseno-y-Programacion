package cl.uchile.dcc.citric
package controller.states

import controller.GameController
import model.unit.player.PlayerCharacter
import model.panel.{HomePanel, NeutralPanel}
import exceptions.{InvalidInputException, InvalidTransitionException}

import org.junit.Assert.assertThrows


class ChapterStateTest extends munit.FunSuite{

  private var controller: GameController = _
  private var state: ChapterState = _
  private var player: PlayerCharacter = _
  private var panel: NeutralPanel = _

  override def beforeEach(context: BeforeEach): Unit = {

    controller = new GameController
    state = new ChapterState
    player = new PlayerCharacter("Toro", 1, 1, 1, 1)
    panel = new NeutralPanel

    panel.addCharacter(player)
    state.controller = controller
    controller.selected = player
    controller.panel = panel

  }

  test("A state should be able to add a controller") {
    assertEquals(state.controller, controller)
  }



  test("Invalid transitions Test") {
    assertThrows(classOf[InvalidTransitionException], () => state.startGame())
    assertThrows(classOf[InvalidTransitionException], () => state.rollDice())
    assertThrows(classOf[InvalidTransitionException], () => state.doEffect())
    assertThrows(classOf[InvalidTransitionException], () => state.stopMovement())
    assertThrows(classOf[InvalidTransitionException], () => state.endCombat())
    assertThrows(classOf[InvalidTransitionException], () => state.choosePath(1))
    assertThrows(classOf[InvalidTransitionException], () => state.outOfMovements())
    assertThrows(classOf[InvalidTransitionException], () => state.attack(1))
    assertThrows(classOf[InvalidTransitionException], () => state.recoveryRoll())
    assertThrows(classOf[InvalidTransitionException], () => state.respond(1))
  }



}