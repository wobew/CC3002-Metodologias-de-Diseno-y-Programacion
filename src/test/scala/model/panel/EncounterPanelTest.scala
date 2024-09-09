package cl.uchile.dcc.citric
package model.panel

import model.unit.GameUnit

import cl.uchile.dcc.citric.model.norma.Norma1
import cl.uchile.dcc.citric.model.unit.player.PlayerCharacter
import cl.uchile.dcc.citric.model.unit.wild.{Chicken, RoboBall, Seagull}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class EncounterPanelTest extends munit.FunSuite  {
  /*
  we use constants for the values that are used in multiple
  tests, so we can change them in a single place
  */

  private var panel: EncounterPanel = _
  private var panel2: EncounterPanel = _
  private var neutralPanel: NeutralPanel = _
  private var homePanel: HomePanel = _
  private var encounterPanel: EncounterPanel = _
  private var dropPanel: DropPanel = _
  private var bonusPanel: BonusPanel = _
  private var chicken: Chicken = _
  private var roboBall: RoboBall = _
  private var seagull: Seagull = _


  private val name = "testPlayer"
  private val maxHp = 10
  private val attack = 1
  private val defense = 1
  private val evasion = 1
  private val randomNumberGenerator = new Random(11)

  private var character: PlayerCharacter = _
  private var norma: Norma1 = _

  override def beforeEach(context: BeforeEach): Unit = {
    panel = new EncounterPanel()
    panel2 = new EncounterPanel()
    chicken = new Chicken(panel2)
    roboBall = new RoboBall(panel2)
    seagull = new Seagull(panel2)


    character = new PlayerCharacter(
      name,
      maxHp,
      attack,
      defense,
      evasion,
      randomNumberGenerator,
    )

    neutralPanel = new NeutralPanel()
    homePanel = new HomePanel(character)
    encounterPanel = new EncounterPanel()
    dropPanel = new DropPanel()
    bonusPanel = new BonusPanel()
    norma = new Norma1(character)

  }

  test("An encounter panel should be with some wilds and no characters") {
    assertEquals(panel.characters, ArrayBuffer.empty[PlayerCharacter].toList)
    assertEquals(panel.nextPanels, ArrayBuffer.empty[Panel].toList)
    assertEquals(panel.wilds.length, 3)
  }

  test("An EncounterPanel should be able to add a character") {
    panel.addCharacter(character)
    assert(panel.characters.contains(character))
  }

  test("An EncounterPanel should be able to remove a character") {
    panel.addCharacter(character)
    panel.removeCharacter(character)
    assert(!panel.characters.contains(character))
  }

  test("Encounter panels should be able to add and remove wild units correctly") {
    assert(panel2.wilds.contains(chicken) && panel2.wilds.contains(roboBall) && panel2.wilds.contains(seagull))
    panel2.removeWild(chicken)
    panel2.removeWild(roboBall)
    panel2.removeWild(seagull)
    assert(!panel2.wilds.contains(chicken) && !panel2.wilds.contains(roboBall) && !panel2.wilds.contains(seagull))
    panel.addWild(chicken)
    panel.addWild(roboBall)
    panel.addWild(seagull)
    assert(panel.wilds.contains(chicken) && panel.wilds.contains(roboBall) && panel.wilds.contains(seagull))
    panel.removeWild(chicken)
    panel.removeWild(roboBall)
    panel.removeWild(seagull)
    assert(!panel.wilds.contains(chicken) && !panel.wilds.contains(roboBall) && !panel.wilds.contains(seagull))
  }

  test("An encounter panel should be able to connect a neutral panel correctly") {
    panel.addPanel(neutralPanel)
    assert(panel.nextPanels.contains(neutralPanel))
  }

  test("An encounter panel should be able to remove a neutral panel correctly") {
    panel.addPanel(neutralPanel)
    panel.removePanel(neutralPanel)
    assert(!panel.nextPanels.contains(neutralPanel))
  }

  test("An encounter panel should be able to add a home panel correctly") {
    panel.addPanel(homePanel)
    assert(panel.nextPanels.contains(homePanel))
  }

  test("An encounter panel should be able to remove a home panel correctly") {
    panel.addPanel(homePanel)
    panel.removePanel(homePanel)
    assert(!panel.nextPanels.contains(homePanel))
  }

  test("An encounter panel should be able to add an encounter panel correctly") {
    panel.addPanel(encounterPanel)
    assert(panel.nextPanels.contains(encounterPanel))
  }

  test("An encounter panel should be able to remove a home panel correctly") {
    panel.addPanel(encounterPanel)
    panel.removePanel(encounterPanel)
    assert(!panel.nextPanels.contains(encounterPanel))
  }

  test("An encounter panel should be able to add a drop panel correctly") {
    panel.addPanel(dropPanel)
    assert(panel.nextPanels.contains(dropPanel))
  }

  test("An encounter panel should be able to remove a drop panel correctly") {
    panel.addPanel(dropPanel)
    panel.removePanel(dropPanel)
    assert(!panel.nextPanels.contains(dropPanel))
  }

  test("An encounter panel should be able to add a bonus panel correctly") {
    panel.addPanel(bonusPanel)
    assert(panel.nextPanels.contains(bonusPanel))
  }

  test("An encounter panel should be able to remove a bonus panel correctly") {
    panel.addPanel(bonusPanel)
    panel.removePanel(bonusPanel)
    assert(!panel.nextPanels.contains(bonusPanel))
  }

  test("A panel returns correctly it's name") {
    assertEquals(panel.name, "Encounter Panel")
  }
}