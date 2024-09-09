package cl.uchile.dcc.citric
package exceptions

import model.unit.player.PlayerCharacter

class InputTest extends munit.FunSuite {
  private val character: PlayerCharacter = new PlayerCharacter("Steve", 2, 2, 2, 2)

  test("simulateInput throws InvalidInputException for invalid inputs") {
    val invalidInput = "run"

    interceptMessage[InvalidInputException](s"An invalid input was found -- ${invalidInput}")
      {character.simulateInput(invalidInput)}
  }

}