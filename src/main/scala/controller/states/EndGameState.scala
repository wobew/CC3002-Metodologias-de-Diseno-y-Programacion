package cl.uchile.dcc.citric
package controller.states

/**
 * The ending of the game :')
 * it just prints congratulation message
 */
class EndGameState extends AbstractGameState  {
  println("You win!!!!")

  override def isEnd = true
}
