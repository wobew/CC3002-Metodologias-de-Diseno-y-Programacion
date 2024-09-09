package cl.uchile.dcc.citric
package controller.states

import exceptions.InvalidInputException

/**
 * The state that encapsulates the movement of a player in their turn
 */
class MovingState extends AbstractGameState {

  /**
   * A player can stop their movement freely if is in their Home Panel
   */
  override def stopMovement(): Unit = {
    if(controller.selected.inHome) {
      controller.changeState(new CombatState())
    }
  }

  /**
   * If a player is without any movements remaining, its forced to stop
   * their movement and transition to a combat state
   */
  override def outOfMovements(): Unit = {
    if(controller.roll == 0) {
      controller.changeState(new CombatState())
    }
  }

  /**
   * A player can choose between the multiple panels adjacent to the current panel to move,
   * if the player is on their Home Panel, it can also choose to stay
   *
   * @param input the option chosen by the player to move
   */
  override def choosePath(input: Int): Unit = {
    controller.movingPrompt()
    if (0 < input && input <= controller.panel.nextPanels.length) {
      controller.moveTo(input)
      if(controller.roll == 0) outOfMovements()
      else controller.changeState(new MovingState())
    }

    else if(controller.selected.inHome && input == controller.panel.nextPanels.length + 1){
      stopMovement()
    }

    else throw new InvalidInputException(input.toString)
  }

  override def isMoving: Boolean = true
}