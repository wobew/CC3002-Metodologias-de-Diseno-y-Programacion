package cl.uchile.dcc.citric
package controller.states

import exceptions.InvalidInputException

/**
 * The state to wait the attacked player input in combat
 */
class WaitState() extends AbstractGameState() {

  /**
   * Simulates the input of the attacked player and finally calls the doAttack method of the attacker knowing the
   * attacked response to simulate a full combat interaction.
   *
   * @param input the response of the attacked player, essentially evade or defend
   */
  override def respond(input: Int):Unit = {
    println("What will you do?")
    println("1 - Evade")
    println("2 - Defense")
    if(input == 1) controller.doAttack("evade")
    else if(input == 2) controller.doAttack("defend")
    else throw new InvalidInputException(input.toString)
    controller.changeState(new CombatState)
  }

  override def isWait: Boolean = true

}