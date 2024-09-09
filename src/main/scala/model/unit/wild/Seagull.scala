package cl.uchile.dcc.citric
package model.unit.wild

import model.panel.EncounterPanel

/** Seagull is a concrete implementation of an AbstractWild unit.
 *
 * This unit represents a Seagull with specific stats, often encountered in EncounterPanels.
 *
 * @param panel The EncounterPanel where this Seagull is located.
 *
 * @constructor Creates a Seagull unit with specific stats and adds it to the specified EncounterPanel.
 *
 * @example
 * {{{
 * val seagull = new Seagull(panel)
 * val attack = seagull.attack()
 * println(s"The attack of the seagull is $attack")
 * }}}
 *
 * @author [[https://github.com/wobew/ Roberto Rivera C.]]
 */
class Seagull(panel: EncounterPanel) extends AbstractWild(3, 1, -1, -1, panel) {
  stars += 2

  override def respawn(): Unit = {
    new Seagull(panel)
  }
}