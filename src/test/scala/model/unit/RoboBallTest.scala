package cl.uchile.dcc.citric
package model.unit

import model.panel.{EncounterPanel, Panel}

import cl.uchile.dcc.citric.model.unit.player.PlayerCharacter
import cl.uchile.dcc.citric.model.unit.wild.{Chicken, RoboBall}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


class RoboBallTest extends munit.FunSuite {

  private val name = "testPlayer"
  private val maxHp = 10
  private val attack = 1
  private val defense = 1
  private val evasion = 1
  private var chicken2: Chicken = _
  private var character: PlayerCharacter = _

  private var panel: EncounterPanel = _
  private var roboball: RoboBall = _

  override def beforeEach(context: BeforeEach): Unit = {

    panel = new EncounterPanel()
    roboball = new RoboBall(panel)

    chicken2 = new Chicken(panel)

    val randomNumberGenerator = new Random(11)

    character = new PlayerCharacter(
      name,
      maxHp,
      attack,
      defense,
      evasion,
      randomNumberGenerator
    )
  }

  test("A roboball should be created with its right attributes") {
    assertEquals(roboball.maxHp, 3)
    assertEquals(roboball.attack, -1)
    assertEquals(roboball.defense, 1)
    assertEquals(roboball.evasion, -1)
    assertEquals(roboball.panel, panel)
  }

  test("Getters test") {
    assertEquals(roboball.hp, 3)
    assertEquals(roboball.stars, 2)
  }

  test("Setters test") {
    roboball.removeHp(1)
    roboball.addStars(2)
    assertEquals(roboball.hp, 2)
    assertEquals(roboball.stars, 4)
  }

  test("Setters should not be negative") {
    roboball.removeHp(10)
    roboball.removeStars(10)
    assertEquals(roboball.hp, 0)
    assertEquals(roboball.stars, 0)
  }

  test("The encounter panel should contain the roboball") {
    assert(panel.wilds.contains(roboball))
  }

  test("A roboball should be able to be removed from the panel") {
    roboball.remove()
    assert(!panel.wilds.contains(roboball))
  }


  test("A roboball should be able to take damage") {
    val dmg = 2
    roboball.takeDamage(dmg)
    assertEquals(roboball.hp, 1)
  }

  test("A roboball should be removed if it takes enough damage") {
    val dmg = 3
    roboball.takeDamage(dmg)
    assertEquals(roboball.hp, 0)
    assert(!panel.wilds.contains(roboball))
  }

  test("A roboball should never be in negative Hp") {
    val dmg = 10
    roboball.takeDamage(dmg)
    assertEquals(roboball.hp, 0)
  }

  test("A roboball should be able to attack a player") {
    roboball.doAttack(character)
    assert(character.hp <= character.maxHp)
  }

  /*
  test("A roboball should not be able to attack another Wild") {
    roboball.doAttack(chicken2)
    assertEquals(chicken2.hp, 3)
  }

  test("A roboball should not be attacked by another Wild") {
    roboball.attackWild(chicken2, 1000)
    assertEquals(roboball.hp, 3)
  }

   */

  test("A roboball should not be able to attack if it's hp is 0") {
    roboball.removeHp(3)
    roboball.doAttack(character)
    assertEquals(character.hp, 10)
  }

  test("A roboball should not be able to attack if it's opponent is KO") {
    character.KO = true
    roboball.doAttack(character)
    assertEquals(character.hp, character.maxHp)
  }

  test("A roboball should be able to evade an attack") {
    roboball.evade(1)
    assertEquals(character.hp, character.maxHp)
  }

  test("A roboball should be able to defend an attack") {
    roboball.defend(3)
    assert(roboball.hp >= 0)
    assert(roboball.hp < 3)
  }

  test("If a roboball defends, it should take at least 1HP of damage") {
    roboball.defend(1)
    assertEquals(roboball.hp, 2)
  }

  test("A roboball should be able to be attacked by a Player and give them stars and victories if killed") {
    roboball.attackPlayer(character, 100)
    assertEquals(roboball.hp, 0)
    assertEquals(character.stars, 2)
    assertEquals(character.victories, 1)
  }

}