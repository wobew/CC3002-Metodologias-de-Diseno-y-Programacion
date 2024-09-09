package cl.uchile.dcc.citric
package controller.states

import controller.GameController
import model.unit.player.PlayerCharacter
import model.panel.NeutralPanel
import exceptions.{InvalidInputException, InvalidTransitionException}

import org.junit.Assert.assertThrows


class WaitStateTest extends munit.FunSuite{

  private var controller: GameController = _
  private var state: WaitState = _
  private var player: PlayerCharacter = _
  private var player2: PlayerCharacter = _
  private var panel: NeutralPanel = _

  override def beforeEach(context: BeforeEach): Unit = {

    controller = new GameController
    state = new WaitState
    player = new PlayerCharacter("Dela", 100, 100, 100, 100)
    player2 = new PlayerCharacter("Rob", 1, 1, 1, 1)
    panel = new NeutralPanel

    panel.addCharacter(player)
    panel.addCharacter(player2)
    state.controller = controller
    controller.selected = player2
    controller.target = player
    controller.panel = panel

  }

  test("A state should be able to add a controller") {
    assertEquals(state.controller, controller)
  }

  test("A player can choose to defend an attack") {
    state.respond(2)
    assertEquals(controller.target.hp, 99)
    assertEquals(controller.state.isChapter, false)
    assertEquals(controller.state.isCombat, true)
    assertEquals(controller.state.isEnd, false)
    assertEquals(controller.state.isLanding, false)
    assertEquals(controller.state.isMoving, false)
    assertEquals(controller.state.isTurn, false)
    assertEquals(controller.state.isRecovery, false)
    assertEquals(controller.state.isStart, false)
    assertEquals(controller.state.isWait, false)
  }

  test("A player can choose to evade an attack") {
    state.respond(1)
    assertEquals(controller.target.hp, 100)
    assertEquals(controller.state.isChapter, false)
    assertEquals(controller.state.isCombat, true)
    assertEquals(controller.state.isEnd, false)
    assertEquals(controller.state.isLanding, false)
    assertEquals(controller.state.isMoving, false)
    assertEquals(controller.state.isTurn, false)
    assertEquals(controller.state.isRecovery, false)
    assertEquals(controller.state.isStart, false)
    assertEquals(controller.state.isWait, false)
  }

  test("Invalid input test") {
    assertThrows(classOf[InvalidInputException], () => state.respond(4))
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
    assertThrows(classOf[InvalidTransitionException], () => state.recoveryRoll())
  }



}