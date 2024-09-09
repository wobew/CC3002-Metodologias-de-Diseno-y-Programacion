package cl.uchile.dcc.citric
package controller.states

/**
 * The very start of the turn of a player! awesome
 */
class PlayerTurnState extends AbstractGameState {


  /**
   * a player can only roll his dice to know how far he can advance and then transitions to a new
   * Moving State
   */
  override def rollDice(): Unit = {
    controller.playerRoll()
    controller.changeState(new MovingState())
  }

  override def isTurn: Boolean = true
}