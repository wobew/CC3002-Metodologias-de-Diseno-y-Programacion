package cl.uchile.dcc.citric
package model.panel

import model.norma.Norma1
import cl.uchile.dcc.citric.model.unit.player.PlayerCharacter
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class NeutralPanelTest extends munit.FunSuite  {
  /*
  we use constants for the values that are used in multiple
  tests, so we can change them in a single place
  */

  private var panel: NeutralPanel = _
  private var neutralPanel: NeutralPanel = _
  private var homePanel: HomePanel = _
  private var encounterPanel: EncounterPanel = _
  private var dropPanel: DropPanel = _
  private var bonusPanel: BonusPanel = _

  private val name = "testPlayer"
  private val maxHp = 10
  private val attack = 1
  private val defense = 1
  private val evasion = 1
  private val randomNumberGenerator = new Random(11)

  private var character: PlayerCharacter = _
  private var norma: Norma1 = _

  override def beforeEach(context: BeforeEach): Unit = {
    panel = new NeutralPanel()

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

  test("A panel should be created empty and disconnected from other panels"){
    assertEquals(panel.characters, ArrayBuffer.empty[PlayerCharacter].toList)
    assertEquals(panel.nextPanels, ArrayBuffer.empty[Panel].toList)
  }

  test("A panel should be able to add a character") {
    panel.addCharacter(character)
    assert(panel.characters.contains(character))
  }

  test("A panel should be able to remove a character") {
    panel.addCharacter(character)
    panel.removeCharacter(character)
    assert(!panel.characters.contains(character))
  }

  test("A neutral panel should be able to connect a neutral panel correctly") {
    panel.addPanel(neutralPanel)
    assert(panel.nextPanels.contains(neutralPanel))
  }

  test("A neutral panel should be able to remove a neutral panel correctly") {
    panel.addPanel(neutralPanel)
    panel.removePanel(neutralPanel)
    assert(!panel.nextPanels.contains(neutralPanel))
  }

  test("A neutral panel should be able to add a home panel correctly") {
    panel.addPanel(homePanel)
    assert(panel.nextPanels.contains(homePanel))
  }

  test("A neutral panel should be able to remove a home panel correctly") {
    panel.addPanel(homePanel)
    panel.removePanel(homePanel)
    assert(!panel.nextPanels.contains(homePanel))
  }

  test("A neutral panel should be able to add an encounter panel correctly") {
    panel.addPanel(encounterPanel)
    assert(panel.nextPanels.contains(encounterPanel))
  }

  test("A neutral panel should be able to remove a home panel correctly") {
    panel.addPanel(encounterPanel)
    panel.removePanel(encounterPanel)
    assert(!panel.nextPanels.contains(encounterPanel))
  }

  test("A neutral panel should be able to add a drop panel correctly") {
    panel.addPanel(dropPanel)
    assert(panel.nextPanels.contains(dropPanel))
  }

  test("A neutral panel should be able to remove a drop panel correctly") {
    panel.addPanel(dropPanel)
    panel.removePanel(dropPanel)
    assert(!panel.nextPanels.contains(dropPanel))
  }

  test("A neutral panel should be able to add a bonus panel correctly") {
    panel.addPanel(bonusPanel)
    assert(panel.nextPanels.contains(bonusPanel))
  }

  test("A neutral panel should be able to remove a bonus panel correctly") {
    panel.addPanel(bonusPanel)
    panel.removePanel(bonusPanel)
    assert(!panel.nextPanels.contains(bonusPanel))
  }

  test("Apply method does nothing") {
    panel.apply(character)
    assertEquals(panel.characters, ArrayBuffer.empty[PlayerCharacter].toList)
    assertEquals(panel.nextPanels, ArrayBuffer.empty[Panel].toList)
    assertEquals(character.name, name)
    assertEquals(character.maxHp, maxHp)
    assertEquals(character.attack, attack)
    assertEquals(character.defense, defense)
    assertEquals(character.evasion, evasion)
    assertEquals(character.inHome, true)
    assertEquals(character.KO, false)
    assertEquals(character.norma, 1)
    assertEquals(character.normaClass, norma)
    assertEquals(character.victories, 0)
    assertEquals(character.stars, 0)
  }

  test("A panel returns correctly it's name") {
    assertEquals(panel.name, "Neutral Panel")
  }
}