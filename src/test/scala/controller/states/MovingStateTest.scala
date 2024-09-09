package cl.uchile.dcc.citric
package controller.states

import controller.GameController
import model.unit.player.PlayerCharacter
import model.panel.{HomePanel, NeutralPanel}
import exceptions.{InvalidInputException, InvalidTransitionException}

import org.junit.Assert.assertThrows


class MovingStateTest extends munit.FunSuite{

  private var controller: GameController = _
  private var state: MovingState = _
  private var player: PlayerCharacter = _
  private var panel: NeutralPanel = _
  private var panel2: NeutralPanel = _
  private var panel3: HomePanel = _

  override def beforeEach(context: BeforeEach): Unit = {

    controller = new GameController
    state = new MovingState
    player = new PlayerCharacter("Martin Reds", 1, 1, 1, 1)
    panel = new NeutralPanel
    panel2 = new NeutralPanel
    panel3 = new HomePanel(player)

    panel3.removeCharacter(player)
    panel.addCharacter(player)

    panel.addPanel(panel2)
    panel.addPanel(panel3)

    panel3.addPanel(panel)
    panel3.addPanel(panel2)

    state.controller = controller
    controller.selected = player
    controller.panel = panel

  }

  test("A state should be able to add a controller") {
    assertEquals(state.controller, controller)
  }

  test("A player can choose to move between multiple panels - test 1") {
    assertEquals(controller.panel, panel)
    assert(panel.characters.contains(player))
    assert(!panel2.characters.contains(player))
    assert(!panel3.characters.contains(player))
    state.choosePath(1)
    assertEquals(controller.panel, panel2)
    assert(!panel.characters.contains(player))
    assert(panel2.characters.contains(player))
    assert(!panel3.characters.contains(player))
    assertEquals(controller.state.isCombat, true)
  }

  test("A player can choose to move between multiple panels - test 2") {
    assertEquals(controller.panel, panel)
    assert(panel.characters.contains(player))
    assert(!panel2.characters.contains(player))
    assert(!panel3.characters.contains(player))
    state.choosePath(2)
    assertEquals(controller.panel, panel3)
    assert(!panel.characters.contains(player))
    assert(!panel2.characters.contains(player))
    assert(panel3.characters.contains(player))
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


  test("A player can stop its movement in its home panel") {
    controller.moveTo(2)
    state.stopMovement()
    assertEquals(controller.panel, panel3)
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

  test("A player can stop its movement in its home panel - test 2") {
    assertEquals(controller.panel, panel)
    assert(panel.characters.contains(player))
    assert(!panel2.characters.contains(player))
    assert(!panel3.characters.contains(player))
    state.choosePath(2)
    controller.playerRoll()
    state.choosePath(3)
    assertEquals(controller.panel, panel3)
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

  test("If a player has enough rolls they can continue moving") {
    assertEquals(controller.panel, panel)
    while(controller.roll < 2) controller.playerRoll()
    assert(panel.characters.contains(player))
    assert(!panel2.characters.contains(player))
    assert(!panel3.characters.contains(player))
    state.choosePath(2)
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

  test("Invalid input test") {
    assertEquals(controller.panel, panel)
    assert(panel.characters.contains(player))
    assert(!panel2.characters.contains(player))
    assert(!panel3.characters.contains(player))
    assertThrows(classOf[InvalidInputException], () => state.choosePath(50))
  }

  test("Invalid transitions Test") {
    assertThrows(classOf[InvalidTransitionException], () => state.startGame())
    assertThrows(classOf[InvalidTransitionException], () => state.rollDice())
    assertThrows(classOf[InvalidTransitionException], () => state.doEffect())
    assertThrows(classOf[InvalidTransitionException], () => state.newChapter())
    assertThrows(classOf[InvalidTransitionException], () => state.playTurn())
    assertThrows(classOf[InvalidTransitionException], () => state.endCombat())
    assertThrows(classOf[InvalidTransitionException], () => state.normaSix())
    assertThrows(classOf[InvalidTransitionException], () => state.knockedOut())
    assertThrows(classOf[InvalidTransitionException], () => state.attack(1))
    assertThrows(classOf[InvalidTransitionException], () => state.recoveryRoll())
    assertThrows(classOf[InvalidTransitionException], () => state.respond(1))
  }



}