package cl.uchile.dcc.citric
package model.unit.wild

import model.panel.EncounterPanel
import model.unit.GameUnit

/**
 * Trait extending GameUnit to provide additional attributes and functionalities specific to wild units in the game.
 *
 * @note This serves as a base trait for non-player characters that can be encountered on EncounterPanels.
 *
 * @author [[https://github.com/wobew Roberto Rivera C.]]
 */
trait WildUnit extends GameUnit {

  /**
   * The EncounterPanel where this wild unit can be encountered.
   */
  def panel: EncounterPanel

}