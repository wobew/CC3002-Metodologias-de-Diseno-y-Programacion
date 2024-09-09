package cl.uchile.dcc.citric
package controller.states


/**
 * This states represents a Chapter
 */
class ChapterState extends AbstractGameState {

  /**
   * initiates the turn of the next player
   */
  override def playTurn(): Unit = {
    println("Player Turn, roll the dice")
    controller.selectNextPlayer()
    controller.changeState(new PlayerTurnState)
  }

  /**
   * pretended to be called once all the players have done their turn, the chapter advances an gives
   * every player some stars
   */
  override def newChapter(): Unit = {
    controller.chapter += 1
    controller.stars()
    controller.changeState(new ChapterState)
  }

  /**
   * When a player is knocked out
   */
  override def knockedOut(): Unit = {
    if (controller.selected.KO){
      println("Roll a dice to recover!")
      controller.changeState(new RecoveryState)
    }
  }

  override def isChapter: Boolean = true
}
