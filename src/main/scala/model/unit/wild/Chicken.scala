package cl.uchile.dcc.citric
package model.unit.wild

import model.panel.EncounterPanel

/** A class representing a chicken
 *
 * Chicken is a concrete implementation of an AbstractWild unit.
 * This unit represents a chicken with specific stats, often encountered in EncounterPanels.
 *
 * @param panel The EncounterPanel where this chicken is located.
 *
 * @constructor Creates a chicken unit with specific stats and adds it to the specified EncounterPanel.
 *
 * @example
 * {{{
 * val chicken = new Chicken(panel)
 * val attack = chicken.attack()
 * println(s"The attack of the chicken is $attack")
 * }}}
 *
 * @author [[https://github.com/wobew/ Roberto Rivera C.]]
 */
class Chicken(panel: EncounterPanel) extends AbstractWild(3, -1, -1, 1, panel) {
  stars += 3

  override def respawn(): Unit = {
    new Chicken(panel)
  }
}