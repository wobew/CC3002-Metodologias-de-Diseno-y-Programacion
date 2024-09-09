package cl.uchile.dcc.citric
package controller.states

import exceptions.InvalidInputException

/**
 * Simulates the combat between 2 players
 */
class CombatState extends AbstractGameState {

  /**
   * Ending a combat will transition to the landing panel state
   */
  override def endCombat(): Unit = {
    controller.changeState(new LandingPanelState())
  }

  /**
   * The player arriving a combat possibly panel chooses between one of the many players who also are
   * in that panel or not to combat with any of them, makes the transition to Wait State to wait for
   * the attacked player's response  or calls the end combat transition immediately if the player
   * choses not to fight
   *
   * @param input the number in of the selection
   */
  override def attack(input: Int): Unit = {
    controller.combatPrompt()
    if (0 < input && input <= controller.panel.characters.length) {
      controller.target_=(controller.panel.characters(input - 1))
      controller.panel.addCharacter(controller.selected)
      controller.changeState(new WaitState())
    }
    else if(input == controller.panel.characters.length +1) endCombat()
    else throw new InvalidInputException(input.toString)
  }

  override def isCombat: Boolean = true
}