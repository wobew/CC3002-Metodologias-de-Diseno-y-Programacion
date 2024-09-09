package cl.uchile.dcc.citric
package model.panel
import controller.GameController
import model.unit.player.PlayerCharacter

/** NeutralPanel is a concrete implementation of an AbstractPanel.
 *
 * This panel serves as a neutral area and does not provide any special functionalities.
 *
 * @constructor Creates a NeutralPanel.
 *
 * @example
 * {{{
 * val panel = new NeutralPanel()
 * panel is an empty neutral panel
 * }}}
 *
 * @author [[https://github.com/wobew Roberto Rivera C.]]
 */
class NeutralPanel() extends AbstractPanel {
  // No additional functionalities are added.
  def name: String = {
    "Neutral Panel"
  }

}
