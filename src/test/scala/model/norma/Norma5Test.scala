package cl.uchile.dcc.citric
package model.norma

import cl.uchile.dcc.citric.model.unit.player.PlayerCharacter
import scala.util.Random

class Norma5Test extends munit.FunSuite  {

  private val name = "testPlayer"
  private val maxHp = 10
  private val attack = 1
  private val defense = 1
  private val evasion = 1
  private val randomNumberGenerator = new Random(11)

  private var character: PlayerCharacter = _
  private var norma: Norma5 = _

  override def beforeEach(context: BeforeEach): Unit = {

    character = new PlayerCharacter(
      name,
      maxHp,
      attack,
      defense,
      evasion,
      randomNumberGenerator,
    )

    character.addStars(120)
    character.addVictories(10)
    character.addNorma(4)

    norma = new Norma5(character)
  }

  test("A norma should be created with it's correct parameters") {
    assertEquals(norma.stars, 200)
    assertEquals(norma.victories, 14)
    assertEquals(norma.owner, character)
  }

  test("A norma should upgrade a character by stars") {
    assertEquals(character.norma, 5)
    character.addStars(200)
    norma.upgrade()
    assertEquals(character.norma, 6)
  }

  test("A norma should upgrade a character by victories") {
    assertEquals(character.norma, 5)
    character.addVictories(14)
    norma.upgrade()
    assertEquals(character.norma, 6)
  }

}