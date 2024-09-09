package cl.uchile.dcc.citric
package model.unit

import model.panel.{EncounterPanel, Panel}

import cl.uchile.dcc.citric.model.unit.player.PlayerCharacter
import cl.uchile.dcc.citric.model.unit.wild.{Chicken, Seagull}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


class SeagullTest extends munit.FunSuite {

  private val name = "testPlayer"
  private val maxHp = 10
  private val attack = 1
  private val defense = 1
  private val evasion = 1
  private var chicken2: Chicken = _
  private var character: PlayerCharacter = _

  private var panel: EncounterPanel = _
  private var seagull: Seagull = _

  override def beforeEach(context: BeforeEach): Unit = {

    panel = new EncounterPanel()
    seagull = new Seagull(panel)

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

  test("A seagull should be created with its right attributes") {
    assertEquals(seagull.maxHp, 3)
    assertEquals(seagull.attack, 1)
    assertEquals(seagull.defense, -1)
    assertEquals(seagull.evasion, -1)
    assertEquals(seagull.panel, panel)
  }

  test("Getters test") {
    assertEquals(seagull.hp, 3)
    assertEquals(seagull.stars, 2)
  }

  test("Setters test") {
    seagull.removeHp(1)
    seagull.addStars(2)
    assertEquals(seagull.hp, 2)
    assertEquals(seagull.stars, 4)
  }

  test("Setters should not be negative") {
    seagull.removeHp(100)
    seagull.removeStars(10)
    assertEquals(seagull.hp, 0)
    assertEquals(seagull.stars, 0)
  }

  test("The encounter panel should contain the seagull") {
    assert(panel.wilds.contains(seagull))
  }

  test("A seagull should be able to be removed from the panel") {
    seagull.remove()
    assert(!panel.wilds.contains(seagull))
  }

  test("A seagull should be able to take damage") {
    val dmg = 2
    seagull.takeDamage(dmg)
    assertEquals(seagull.hp, 1)
  }

  test("A seagull should be removed if it takes enough damage") {
    val dmg = 3
    seagull.takeDamage(dmg)
    assertEquals(seagull.hp, 0)
    assert(!panel.wilds.contains(seagull))
  }

  test("A seagull should never be in negative Hp") {
    val dmg = 10
    seagull.takeDamage(dmg)
    assertEquals(seagull.hp, 0)
  }

  test("A seagull should be able to attack a player") {
    seagull.doAttack(character)
    assert(character.hp <= character.maxHp)
  }

  /*
  test("A seagull should not be able to attack another Wild") {
    seagull.doAttack(chicken2)
    assertEquals(chicken2.hp, 3)
  }

  test("A seagull should not be attacked by another Wild") {
    seagull.attackWild(chicken2, 1000)
    assertEquals(seagull.hp, 3)
  }

   */

  test("A seagull should not be able to attack if it's hp is 0") {
    seagull.removeHp(3)
    seagull.doAttack(character)
    assertEquals(character.hp, 10)
  }

  test("A seagull should not be able to attack if it's opponent is KO") {
    character.KO = true
    seagull.doAttack(character)
    assertEquals(character.hp, character.maxHp)
  }

  test("A seagull should be able to evade an attack") {
    seagull.evade(1)
    assertEquals(character.hp, character.maxHp)
  }

  test("A seagull should be able to defend an attack") {
    seagull.defend(3)
    assert(seagull.hp >= 0)
    assert(seagull.hp < 3)
  }

  test("If a seagull defends, it should take at least 1HP of damage") {
    seagull.defend(1)
    assertEquals(seagull.hp, 2)
  }

  test("A seagull should be able to be attacked by a Player and give them stars and victories if killed") {
    seagull.attackPlayer(character, 100)
    assertEquals(seagull.hp, 0)
    assertEquals(character.stars, 2)
    assertEquals(character.victories, 1)
  }

}