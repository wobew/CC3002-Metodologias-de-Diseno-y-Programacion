package cl.uchile.dcc.citric
package controller.states

import controller.GameController
import model.unit.player.PlayerCharacter
import model.panel.{BonusPanel, DropPanel, EncounterPanel, HomePanel, NeutralPanel}
import exceptions.{InvalidInputException, InvalidTransitionException}

import cl.uchile.dcc.citric.model.norma.Norma1
import org.junit.Assert.assertThrows


class LandingPanelStateTest extends munit.FunSuite{

  private var controller: GameController = _
  private var state: LandingPanelState = _
  private var player: PlayerCharacter = _
  private var player2: PlayerCharacter = _
  private var neutral: NeutralPanel = _
  private var home: HomePanel = _
  private var encounter: EncounterPanel = _
  private var drop: DropPanel = _
  private var bonus: BonusPanel = _

  override def beforeEach(context: BeforeEach): Unit = {

    controller = new GameController
    state = new LandingPanelState
    player = new PlayerCharacter("Caldecrack", 100, 0, 0, 0)
    player2 = new PlayerCharacter("Frankgod", 100, 2000, 1, 1)
    neutral = new NeutralPanel
    home = new HomePanel(player)
    encounter = new EncounterPanel
    drop = new DropPanel
    bonus = new BonusPanel

    state.controller = controller
    controller.selected = player
    player.removeHp(1)
    player.addStars(10)
    controller.chapter = 1
    new Norma1(player)

  }

  test("A state should be able to add a controller") {
    assertEquals(state.controller, controller)
  }

  test("Neutral Panel Test") {
    controller.panel = neutral
    neutral.addCharacter(player)
    state.doEffect()
    assertEquals(player.hp, 99)
    assertEquals(player.stars, 10)
    assertEquals(player.victories, 0)
    assertEquals(player.norma, 1)
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

  test("Home Panel Test") {
    controller.panel = home
    state.doEffect()
    assertEquals(player.hp, 100)
    assertEquals(player.stars, 10)
    assertEquals(player.victories, 0)
    assertEquals(player.norma, 2)
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


  test("Drop Panel Test") {
    controller.panel = drop
    drop.addCharacter(player)
    state.doEffect()
    assertEquals(player.hp, 99)
    assert(player.stars < 10)
    assertEquals(player.victories, 0)
    assertEquals(player.norma, 1)
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

  test("Bonus Panel Test") {
    controller.panel = bonus
    bonus.addCharacter(player)
    state.doEffect()
    assertEquals(player.hp, 99)
    assert(player.stars > 10)
    assertEquals(player.victories, 0)
    assertEquals(player.norma, 1)
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



  /*
  test("Encounter Panel Test - player receives damage") {
    controller.panel = encounter
    encounter.addCharacter(player)
    state.doEffect("defend")
    assert(player.hp < 99)
    assertEquals(player.stars, 1000)
    assertEquals(player.victories, 0)
    assertEquals(player.norma, 1)
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
   */

  test("Encounter Panel Test - player insta kills the wild and receives stars and victories") {
    controller.panel = encounter
    controller.selected = player2
    encounter.addCharacter(player2)
    state.doEffect()
    assertEquals(player2.hp, 100)
    assert(player2.stars > 0)
    assertEquals(player2.victories, 1)
    assertEquals(player2.norma, 1)
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
    assertThrows(classOf[InvalidTransitionException], () => state.startGame())
    assertThrows(classOf[InvalidTransitionException], () => state.rollDice())
    assertThrows(classOf[InvalidTransitionException], () => state.newChapter())
    assertThrows(classOf[InvalidTransitionException], () => state.playTurn())
    assertThrows(classOf[InvalidTransitionException], () => state.stopMovement())
    assertThrows(classOf[InvalidTransitionException], () => state.endCombat())
    assertThrows(classOf[InvalidTransitionException], () => state.knockedOut())
    assertThrows(classOf[InvalidTransitionException], () => state.choosePath(1))
    assertThrows(classOf[InvalidTransitionException], () => state.outOfMovements())
    assertThrows(classOf[InvalidTransitionException], () => state.attack(1))
    assertThrows(classOf[InvalidTransitionException], () => state.recoveryRoll())
    assertThrows(classOf[InvalidTransitionException], () => state.respond(1))
  }



}