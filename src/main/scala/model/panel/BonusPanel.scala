package cl.uchile.dcc.citric
package model.panel

import model.unit.player.PlayerCharacter

import cl.uchile.dcc.citric.controller.GameController

/** BonusPanel is a concrete implementation of an AbstractPanel.
 *
 * It specializes in giving bonuses to player characters when activated.
 *
 * @constructor Creates a BonusPanel with an initial roll value of 0.
 *
 * @example
 * {{{
 * val panel = new BonusPanel()
 * panel is an empty Bonus Panel
 * }}}
 *
 * @author [[https://github.com/wobew Roberto Rivera C.]]
 */
class BonusPanel() extends AbstractPanel {

  /**
   * Overrides the activate method from AbstractPanel.
   * When a player character activates this panel, it rolls a dice and
   * increases its star count based on the dice roll and other conditions.
   *
   * @param player The PlayerCharacter who activates the panel.
   */
  override def apply(player: PlayerCharacter, _context: Option[GameController] = None, input: String = ""): Unit = {
    if(!characters.contains(player)) return
    val roll: Int = player.rollDice()
    player.addStars(math.min(roll * player.norma, roll * 3))
  }

  def name: String = {
    "Bonus Panel"
  }

}