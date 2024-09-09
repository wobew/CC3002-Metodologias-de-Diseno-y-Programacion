package cl.uchile.dcc.citric
package model.panel

import model.norma.Norma1
import cl.uchile.dcc.citric.model.unit.player.PlayerCharacter

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class HomePanelTest extends munit.FunSuite  {
  /*
  we use constants for the values that are used in multiple
  tests, so we can change them in a single place
  */

  private var panel: HomePanel = _
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

  private var owner: PlayerCharacter = _
  private var character: PlayerCharacter = _
  private var norma: Norma1 = _

  override def beforeEach(context: BeforeEach): Unit = {
    character = new PlayerCharacter(
      name,
      maxHp,
      attack,
      defense,
      evasion,
      randomNumberGenerator
    )

    norma = new Norma1(character)

    owner = new PlayerCharacter(
      name,
      maxHp,
      attack,
      defense,
      evasion,
      randomNumberGenerator
    )

    panel = new HomePanel(owner)
    neutralPanel = new NeutralPanel()
    homePanel = new HomePanel(character)
    encounterPanel = new EncounterPanel()
    dropPanel = new DropPanel()
    bonusPanel = new BonusPanel()

  }

  test("A panel should be created disconnected from other panels and with its owner") {
    val expected = ArrayBuffer.empty[PlayerCharacter]
    expected += owner
    val finalExpected = expected.toList
    assertEquals(panel.nextPanels, ArrayBuffer.empty[Panel].toList)
    assertEquals(panel.characters, finalExpected)
  }

  test("A HomePanel should be initialized whit it's owner (equivalent, the owner should be at home)") {
    assertEquals(panel.ownerIn, true)
    assertEquals(owner.inHome, true)
  }

  test("A HomePanel should be able to add a character") {
    panel.addCharacter(character)
    assert(panel.characters.contains(character))
  }

  test("A HomePanel should be able to remove a character") {
    panel.addCharacter(character)
    panel.removeCharacter(character)
    assert(!panel.characters.contains(character))
  }

  test("A HomePanel should be able to remove it's owner correctly") {
    panel.removeCharacter(owner)
    assertEquals(panel.ownerIn, false)
    assertEquals(owner.inHome, false)
  }

  test("A HomePanel should be able to add it's owner") {
    panel.removeCharacter(owner)
    panel.addCharacter(owner)
    assertEquals(panel.ownerIn, true)
    assertEquals(owner.inHome, true)
  }

  test("A HomePanel should be able to restore a character Hp when it is activated") {
    character.removeHp(3)
    panel.addCharacter(character)
    panel.activate(character)
    assertEquals(character.hp, 8)
  }

  test("A HomePanel should be able to restores its owner Hp when it is activated") {
    owner.removeHp(3)
    panel.activate(owner)
    assertEquals(owner.hp, 8)
  }

  test("A HomePanel should not restore Hp if the players are already at maximum Hp") {
    panel.addCharacter(character)
    panel.activate(character)
    assertEquals(character.hp, maxHp)
    panel.activate(owner)
    assertEquals(owner.hp, maxHp)
  }

  test("A HomePanel should not restore Hp if the players are not in the panel") {
    character.removeHp(5)
    panel.activate(character)
    assertEquals(character.hp, 5)
    panel.removeCharacter(owner)
    owner.removeHp(5)
    panel.activate(owner)
    assertEquals(owner.hp, 5)
  }

  test("A home panel should be able to connect a neutral panel correctly") {
    panel.addPanel(neutralPanel)
    assert(panel.nextPanels.contains(neutralPanel))
  }

  test("A home panel should be able to remove a neutral panel correctly") {
    panel.addPanel(neutralPanel)
    panel.removePanel(neutralPanel)
    assert(!panel.nextPanels.contains(neutralPanel))
  }

  test("A home panel should be able to add a home panel correctly") {
    panel.addPanel(homePanel)
    assert(panel.nextPanels.contains(homePanel))
  }

  test("A home panel should be able to remove a home panel correctly") {
    panel.addPanel(homePanel)
    panel.removePanel(homePanel)
    assert(!panel.nextPanels.contains(homePanel))
  }

  test("A home panel should be able to add an encounter panel correctly") {
    panel.addPanel(encounterPanel)
    assert(panel.nextPanels.contains(encounterPanel))
  }

  test("A home panel should be able to remove a home panel correctly") {
    panel.addPanel(encounterPanel)
    panel.removePanel(encounterPanel)
    assert(!panel.nextPanels.contains(encounterPanel))
  }

  test("A home panel should be able to add a drop panel correctly") {
    panel.addPanel(dropPanel)
    assert(panel.nextPanels.contains(dropPanel))
  }

  test("A home panel should be able to remove a drop panel correctly") {
    panel.addPanel(dropPanel)
    panel.removePanel(dropPanel)
    assert(!panel.nextPanels.contains(dropPanel))
  }

  test("A home panel should be able to add a bonus panel correctly") {
    panel.addPanel(bonusPanel)
    assert(panel.nextPanels.contains(bonusPanel))
  }

  test("A home panel should be able to remove a bonus panel correctly") {
    panel.addPanel(bonusPanel)
    panel.removePanel(bonusPanel)
    assert(!panel.nextPanels.contains(bonusPanel))
  }

  test("A norma check effect should upgrade the player's norma level if the player is in the panel and has enough stars") {
    assertEquals(character.normaClass, norma)
    assertEquals(character.norma, 1)
    panel.addCharacter(character)
    character.addStars(10)
    panel.normaCheck(character)
    assertEquals(character.norma, 2)
  }

  test("A norma check effect should upgrade the player's norma level if the player is in the panel and has enough victories") {
    assertEquals(character.normaClass, norma)
    assertEquals(character.norma, 1)
    panel.addCharacter(character)
    character.addVictories(1)
    panel.normaCheck(character)
    assertEquals(character.norma, 2)
  }

  test("A norma check effect should upgrade as many levels as possible by stars if the player is in the panel") {
    assertEquals(character.normaClass, norma)
    assertEquals(character.norma, 1)
    panel.addCharacter(character)
    character.addStars(120)
    panel.normaCheck(character)
    assertEquals(character.norma, 5)
  }

  test("A norma check effect should upgrade as many levels as possible by victories if the player is in the panel") {
    assertEquals(character.normaClass, norma)
    assertEquals(character.norma, 1)
    panel.addCharacter(character)
    character.addVictories(10)
    panel.normaCheck(character)
    assertEquals(character.norma, 5)
  }

  test("A norma check effect should not upgrade the player's norma level if they doesn't have enough stars or victories") {
    assertEquals(character.normaClass, norma)
    assertEquals(character.norma, 1)
    panel.addCharacter(character)
    panel.normaCheck(character)
    assertEquals(character.norma, 1)
    assertEquals(character.normaClass, norma)
  }

  test("A norma check effect should not upgrade the player's norma level if they aren't in the panel") {
    assertEquals(character.normaClass, norma)
    assertEquals(character.norma, 1)
    character.addStars(998)
    character.addVictories(998)
    panel.normaCheck(character)
    assertEquals(character.norma, 1)
    assertEquals(character.normaClass, norma)
  }

  test("A panel returns correctly it's name") {
    assertEquals(panel.name, "Home Panel")
  }

  test("Apply uses the 2 effects") {
    character.removeHp(1)
    character.addStars(998)
    panel.addCharacter(character)
    panel.apply(character)
    assertEquals(character.norma, 6)
    assertEquals(character.hp, 10)

  }
}