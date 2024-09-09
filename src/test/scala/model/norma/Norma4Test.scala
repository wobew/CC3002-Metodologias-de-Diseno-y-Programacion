package cl.uchile.dcc.citric
package model.norma

import cl.uchile.dcc.citric.model.unit.player.PlayerCharacter
import scala.util.Random

class Norma4Test extends munit.FunSuite  {

  private val name = "testPlayer"
  private val maxHp = 10
  private val attack = 1
  private val defense = 1
  private val evasion = 1
  private val randomNumberGenerator = new Random(11)

  private var character: PlayerCharacter = _
  private var norma: Norma4 = _

  override def beforeEach(context: BeforeEach): Unit = {

    character = new PlayerCharacter(
      name,
      maxHp,
      attack,
      defense,
      evasion,
      randomNumberGenerator,
    )

    character.addStars(70)
    character.addVictories(6)
    character.addNorma(3)

    norma = new Norma4(character)
  }

  test("A norma should be created with it's correct parameters") {
    assertEquals(norma.stars, 120)
    assertEquals(norma.victories, 10)
    assertEquals(norma.owner, character)
  }

  test("A norma should upgrade a character by stars") {
    assertEquals(character.norma, 4)
    character.addStars(120)
    norma.upgrade()
    assertEquals(character.norma, 5)
  }

  test("A norma should upgrade a character by victories") {
    assertEquals(character.norma, 4)
    character.addVictories(4)
    norma.upgrade()
    assertEquals(character.norma, 5)
  }

  test("A norma should upgrade a character more than one level by stars") {
    assertEquals(character.norma, 4)
    character.addStars(200)
    norma.upgrade()
    assertEquals(character.norma, 6)
  }

  test("A norma should upgrade a character more than one level by victories") {
    assertEquals(character.norma, 4)
    character.addVictories(14)
    norma.upgrade()
    assertEquals(character.norma, 6)
  }

}