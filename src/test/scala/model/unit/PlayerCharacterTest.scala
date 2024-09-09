package cl.uchile.dcc.citric
package model.unit

import model.panel.EncounterPanel

import cl.uchile.dcc.citric.model.unit.player.PlayerCharacter
import cl.uchile.dcc.citric.model.unit.wild.{Chicken, RoboBall, Seagull}

import scala.util.Random

class PlayerCharacterTest extends munit.FunSuite {
  /*
  REMEMBER: It is a good practice to use constants for the values that are used in multiple
  tests, so you can change them in a single place.
  This will make your tests more readable, easier to maintain, and less error-prone.
  */
  private val name = "testPlayer"
  private val maxHp = 10
  private val attack = 1
  private val defense = 1
  private val evasion = 1
  /* Add any other constants you need here... */

  /*
  This is the object under test.
  We initialize it in the beforeEach method so we can reuse it in all the tests.
  This is a good practice because it will reset the object before each test, so you don't have
  to worry about the state of the object between tests.
  */
  private var character: PlayerCharacter = _ // <- x = _ is the same as x = null
  /* Add any other variables you need here... */
  private var character2: PlayerCharacter = _

  private var chicken: Chicken = _
  private var roboball: RoboBall = _
  private var seagull: Seagull = _

  private var panel: EncounterPanel = _


  // This method is executed before each `test(...)` method.
  override def beforeEach(context: BeforeEach): Unit = {

    val randomNumberGenerator = new Random(11)

    panel = new EncounterPanel

    chicken = new Chicken(panel)
    seagull = new Seagull(panel)
    roboball = new RoboBall(panel)

    character = new PlayerCharacter(
      name,
      maxHp,
      attack,
      defense,
      evasion,
      randomNumberGenerator
    )
    character2 = new PlayerCharacter(
      name,
      maxHp,
      attack,
      defense,
      evasion
    )
  }

  test("A character should have correctly set their attributes") {
    assertEquals(character.name, name)
    assertEquals(character.maxHp, maxHp)
    assertEquals(character.attack, attack)
    assertEquals(character.defense, defense)
    assertEquals(character.evasion, evasion)
    assertEquals(character.inHome, true)
    assertEquals(character.KO, false)
  }

  test("Getters test") {
    assertEquals(character.hp, maxHp)
    assertEquals(character.stars, 0)
    assertEquals(character.norma, 1)
    assertEquals(character.victories, 0)
  }

  test("Setters test") {
    character.removeHp(7)
    character.addStars(3)
    character.addNorma(2)
    character.addVictories(3)
    assertEquals(character.hp, 3)
    assertEquals(character.stars, 3)
    assertEquals(character.norma, 3)
    assertEquals(character.victories, 3)
  }

  test("Setters should not be negative") {
    character.removeHp(100)
    character.removeStars(1)
    character.addNorma(-10)
    character.removeVictories(1)
    assertEquals(character.hp, 0)
    assertEquals(character.stars, 0)
    assertEquals(character.norma, 0)
    assertEquals(character.victories, 0)
  }

  test("HP setter should not exceed max HP") {
    character.addHp(100)
    assertEquals(character.hp, maxHp)
  }

  // Two ways to test randomness (you can use any of them):

  // 1. Test invariant properties, e.g. the result is always between 1 and 6.
  test("A character should be able to roll a dice") {
    for (_ <- 1 to 10) {
      assert(character.rollDice >= 1 && character.rollDice <= 6)
    }
  }

  // 2. Set a seed and test the result is always the same.
  // A seed sets a fixed succession of random numbers, so you can know that the next numbers
  // are always the same for the same seed.
  test("A character should be able to roll a dice with a fixed seed") {
    val other =
      new PlayerCharacter(name, maxHp, attack, defense, evasion, new Random(11))
    for (_ <- 1 to 10) {
      assertEquals(character.rollDice(), other.rollDice())
    }
  }

  test("A default character should have correctly set their attributes") {
    assertEquals(character2.name, name)
    assertEquals(character2.maxHp, maxHp)
    assertEquals(character2.attack, attack)
    assertEquals(character2.defense, defense)
    assertEquals(character2.evasion, evasion)
    assertEquals(character2.inHome, true)
    assertEquals(character2.KO, false)
  }

  test("A default character should be able to roll a dice") {
    for (_ <- 1 to 10) {
      assert(character2.rollDice >= 1 && character2.rollDice <= 6)
    }
  }

  test("A character should be able to take damage and not be KO if HP > 0") {
    val dmg = 2
    character.takeDamage(dmg)
    assertEquals(character.hp, 8)
    assertEquals(character.KO, false)
  }

  test("A character should be KO if HP = 0") {
    val dmg = 10
    character.takeDamage(dmg)
    assertEquals(character.KO, true)
  }

  test("A character should never be with negative Hp") {
    val dmg = 12
    character.takeDamage(dmg)
    assertEquals(character.hp, 0)
  }

  test("A character should be able to attack somebody") {
    character.doAttack(character2)
    character.doAttack(chicken)
    character.doAttack(roboball)
    character.doAttack(seagull)
    assert(character2.hp <= maxHp)
    assert(chicken.hp < maxHp)
    assert(roboball.hp < maxHp)
    assert(seagull.hp < maxHp)
  }

  test("A character should not be able to attack if is KO") {
    character.takeDamage(1000) // takes enough damage to got KO
    character.doAttack(character2)
    character.doAttack(chicken)
    character.doAttack(roboball)
    character.doAttack(seagull)
    assertEquals(character2.hp, maxHp)
    assertEquals(chicken.hp, chicken.maxHp)
    assertEquals(roboball.hp, roboball.maxHp)
    assertEquals(seagull.hp, seagull.maxHp)
  }

  test("A character should not be able to attack somebody if it's with 0 HP") {
    character2.takeDamage(1000)
    chicken.takeDamage(1000)
    roboball.takeDamage(1000)
    seagull.takeDamage(1000)
    character.doAttack(character2)
    character.doAttack(chicken)
    character.doAttack(roboball)
    character.doAttack(seagull)
    assertEquals(character2.hp, 0)
    assertEquals(chicken.hp, 0)
    assertEquals(roboball.hp, 0)
    assertEquals(seagull.hp, 0)
  }

  test("If a player is killed by another, they should give them half of their stars and increase this player's victories") {
    character2.addStars(9)
    character2.attackPlayer(character, 1000)
    assertEquals(character2.KO, true)
    assertEquals(character.victories, 2)
    assertEquals(character.stars, 4)
    assertEquals(character2.stars, 5)
  }

  test("If a player is killed by a Wild, they should give it half of their stars") {
    character.addStars(10)
    character.attackWild(chicken, 1000)
    assertEquals(character.KO, true)
    assertEquals(character.stars, 5)
    assertEquals(chicken.stars, 8)
  }

  test("A player should be able to evade an attack") {
    character.evade(1)
    assertEquals(character.hp, character.maxHp)
  }

  test("A player should be able to defend an attack") {
    character.defend(10)
    assert(character.hp > 0)
    assert(character.hp < maxHp)
  }

  test("If a player defends, it should take at least 1HP of damage") {
    character.defend(1)
    assertEquals(character.hp, 9)
  }

  test("Simulate input should return evade or defend if the input is the empty string") {

    for (_ <- 1 to 100) {
        val input = character.simulateInput("")
        val condition1 = try {
          assertEquals(input, "evade")
          true
        } catch {
          case _: Throwable => false
        }

        val condition2 = try {
          assertEquals(input, "defend")
          true
        } catch {
          case _: Throwable => false
        }
        assert(condition1 || condition2, "Both conditions failed")
      }
    }

  test("Simulate Input must return the input specified") {
    val input1 = character.simulateInput("defend")
    val input2 = character.simulateInput("evade")
    assertEquals(input1, "defend")
    assertEquals(input2, "evade")
  }

  test("A player can decide to evade damage") {
    character.attackPlayer(character2, 1, "evade")
    assertEquals(character.hp, maxHp)
  }

  test("A player can decide to defend damage") {
    character.attackPlayer(character2, 2, "defend")
    assertEquals(character.hp, 9)
  }

}

