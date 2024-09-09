package cl.uchile.dcc.citric
package model.unit

import model.panel.{EncounterPanel, Panel}

import cl.uchile.dcc.citric.model.unit.player.PlayerCharacter
import cl.uchile.dcc.citric.model.unit.wild.Chicken

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


class ChickenTest extends munit.FunSuite {

  private val name = "testPlayer"
  private val maxHp = 10
  private val attack = 1
  private val defense = 1
  private val evasion = 1
  private var chicken2: Chicken = _
  private var character: PlayerCharacter = _

  private var panel: EncounterPanel = _
  private var chicken: Chicken = _

  override def beforeEach(context: BeforeEach): Unit = {

    panel = new EncounterPanel()
    chicken = new Chicken(panel)
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

  test("A chicken should be created with its right attributes"){
    assertEquals(chicken.maxHp, 3)
    assertEquals(chicken.attack, -1)
    assertEquals(chicken.defense, -1)
    assertEquals(chicken.evasion, 1)
    assertEquals(chicken.panel, panel)
  }

  test("Getters test") {
    assertEquals(chicken.hp, 3)
    assertEquals(chicken.stars, 3)
  }

  test("Setters test") {
    chicken.removeHp(1)
    chicken.removeStars(2)
    assertEquals(chicken.hp, 2)
    assertEquals(chicken.stars, 1)
  }

  test("Setters should not be negative") {
    chicken.removeHp(10)
    chicken.removeStars(10)
    assertEquals(chicken.hp, 0)
    assertEquals(chicken.stars, 0)
  }

  test("The encounter panel should contain the chicken") {
    assert(panel.wilds.contains(chicken))
  }

  test("A chicken should be able to be removed from the panel") {
    chicken.remove()
    assert(!panel.wilds.contains(chicken))
  }


  test("A chicken should be able to take damage") {
    val dmg = 2
    chicken.takeDamage(dmg)
    assertEquals(chicken.hp, 1)
  }

  test("A chicken should be removed if it takes enough damage") {
    val dmg = 3
    chicken.takeDamage(dmg)
    assertEquals(chicken.hp, 0)
    assert(!panel.wilds.contains(chicken))
  }

  test("A chicken should never be in negative Hp") {
    val dmg = 10
    chicken.takeDamage(dmg)
    assertEquals(chicken.hp, 0)
  }

  test("A chicken should be able to attack a player") {
    chicken.doAttack(character)
    assert(character.hp <= character.maxHp)
  }

  /*
  test("A chicken should not be able to attack another Wild") {
    chicken.doAttack(chicken2)
    assertEquals(chicken2.hp, 3)
  }

  test("A chicken should not be attacked by another Wild") {
    chicken.attackWild(chicken2, 1000)
    assertEquals(chicken.hp, 3)
  }
   */

  test("A chicken should not be able to attack if it's hp is 0"){
    chicken.removeHp(3)
    chicken.doAttack(character)
    assertEquals(character.hp, 10)
  }

  test("A chicken should not be able to attack if it's opponent is KO") {
    character.KO = true
    chicken.doAttack(character)
    assertEquals(character.hp, character.maxHp)
  }

  test("A Chicken should be able to evade an attack") {
    chicken.evade(1)
    assertEquals(character.hp, character.maxHp)
  }

  test("A chicken should be able to defend an attack") {
    chicken.defend(3)
    assert(chicken.hp >= 0)
    assert(chicken.hp < 3)
  }

  test("If a chicken defends, it should take at least 1HP of damage") {
    chicken.defend(1)
    assertEquals(chicken.hp, 2)
  }

  test("A chicken should be able to be attacked by a Player and give them stars and victories if killed") {
    chicken.attackPlayer(character, 100)
    assertEquals(chicken.hp, 0)
    assertEquals(character.stars, 3)
    assertEquals(character.victories, 1)
  }

  test("A chicken should be able to be attacked by a Player and give them stars and victories if killed") {
    chicken.attackPlayer(character, 100)
    assertEquals(chicken.hp, 0)
    assertEquals(character.stars, 3)
    assertEquals(character.victories, 1)
  }

  test("A chicken should be able to be attacked by a Player and give them stars and victories if killed") {
    chicken.attackPlayer(character, 100)
    assertEquals(chicken.hp, 0)
    assertEquals(character.stars, 3)
    assertEquals(character.victories, 1)
  }

}
