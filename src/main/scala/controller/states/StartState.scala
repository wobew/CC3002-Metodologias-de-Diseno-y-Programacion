package cl.uchile.dcc.citric
package controller.states

/**
 * The initial state :O
 */
class StartState extends AbstractGameState {

  /**
   * the new game being created by the controller, it transitions to the a new chapter state to start playing
   */
  override def startGame(): Unit = {
    println("A new Game starts!")
    controller.changeState(new ChapterState())
  }

  override def isStart: Boolean = true

}
