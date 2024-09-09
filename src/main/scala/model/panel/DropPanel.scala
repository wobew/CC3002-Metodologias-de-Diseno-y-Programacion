package cl.uchile.dcc.citric
package model.panel

import cl.uchile.dcc.citric.controller.GameController
import cl.uchile.dcc.citric.model.unit.player.PlayerCharacter

/** DropPanel is a concrete implementation of an AbstractPanel.
 *
 * It specializes in dropping stars from player characters when activated.
 *
 * @constructor Creates a DropPanel with an initial roll value of 0.
 *
 * @example
 * {{{
 * val panel = new DropPanel()
 * panel is a empty Drop Panel
 * }}}
 *
 * @author [[https://github.com/wobew Roberto Rivera C.]]
 */
class DropPanel() extends AbstractPanel {

  /** Drops some player's stars
   *
   * Overrides the activate method from AbstractPanel.
   * When a player character activates this panel, it rolls a dice and
   * decreases its star count based on the dice roll and other conditions.
   *
   * @param player The PlayerCharacter who activates the panel.
   */
  override def apply(player: PlayerCharacter, _context: Option[GameController] = None, input: String = ""): Unit = {
    if(!characters.contains(player)) return
    player.removeStars(math.max(player.stars - player.rollDice() * player.norma, 0))
  }

  def name: String = {
    "Drop Panel"
  }
}
