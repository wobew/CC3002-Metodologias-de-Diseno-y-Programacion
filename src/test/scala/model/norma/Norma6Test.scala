package cl.uchile.dcc.citric
package model.norma

import cl.uchile.dcc.citric.model.unit.player.PlayerCharacter
import scala.util.Random

class Norma6Test extends munit.FunSuite  {

  private val name = "testPlayer"
  private val maxHp = 10
  private val attack = 1
  private val defense = 1
  private val evasion = 1
  private val randomNumberGenerator = new Random(11)

  private var character: PlayerCharacter = _
  private var norma: Norma6 = _

  override def beforeEach(context: BeforeEach): Unit = {

    character = new PlayerCharacter(
      name,
      maxHp,
      attack,
      defense,
      evasion,
      randomNumberGenerator,
    )

    character.addStars(200)
    character.addVictories(14)
    character.addNorma(5)

    norma = new Norma6(character)
  }

  test("A norma should be created with it's correct parameters") {
    assertEquals(norma.stars, 999)
    assertEquals(norma.victories, 999)
    assertEquals(norma.owner, character)
  }

  test("A norma 6 should not upgrade a character by stars") {
    assertEquals(character.norma, 6)
    character.addStars(999)
    norma.upgrade()
    assertEquals(character.norma, 6)
    assertEquals(character.normaClass, norma)
  }

  test("A norma 6 should not upgrade a character by victories") {
    assertEquals(character.norma, 6)
    character.addVictories(999)
    norma.upgrade()
    assertEquals(character.norma, 6)
    assertEquals(character.normaClass, norma)
  }

}