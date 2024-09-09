package cl.uchile.dcc.citric
package controller.states

/**
 * A player tries to recover from being knocked out
 */
class RecoveryState extends AbstractGameState {

  /**
   * The player rolls a dice to try to recover, if so they continues normally with their turn. otherwise their map
   *  requirement will be lowered by one (originally initialized in 6) and will transition to the next player's
   *  chapter state.
   */
  override def recoveryRoll(): Unit = {
    controller.playerRoll()
    if(controller.roll >= controller.recovery(controller.selected)){
      controller.changeState(new PlayerTurnState())
      controller.selected.addHp(controller.selected.maxHp / 2)
      controller.recovery(controller.selected) = 6
    } else {
      controller.recovery(controller.selected) -= 1
      controller.changeState(new ChapterState())
      controller.selectNextPlayer()
    }
  }

  override def isRecovery: Boolean = true

}
