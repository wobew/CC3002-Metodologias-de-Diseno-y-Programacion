package cl.uchile.dcc.citric
package controller.states

/**
 * The state when a player finally arrives on a panel and activates its effect
 */
class LandingPanelState extends AbstractGameState {

  /**
   * A method that activates the panel apply effect upon the player, in case it is an Encounter Panel it receives also
   * the input of the player response (we need to simulate input for testing :c). If the player norma is different of
   * 6
   *
   * @param input the response of the player if needed
   */
  override def doEffect(input: String = ""): Unit = {
    controller.panelApply(input)
    if(controller.selected.norma != 6) {
      controller.changeState(new ChapterState())
    }

  }

  /**
   * The transition to be called when the observer is notified about the winning condition
   */
  override def normaSix(): Unit = {
    controller.changeState(new EndGameState)
  }

  override def isLanding: Boolean = true

}