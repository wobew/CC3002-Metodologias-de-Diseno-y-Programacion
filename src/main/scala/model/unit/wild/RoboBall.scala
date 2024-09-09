package cl.uchile.dcc.citric
package model.unit.wild

import model.panel.EncounterPanel

/** RoboBall is a concrete implementation of an AbstractWild unit.
 *
 * This unit represents a RoboBall with specific stats, often encountered in EncounterPanels.
 *
 * @param panel The EncounterPanel where this RoboBall is located.
 *
 * @constructor Creates a RoboBall unit with specific stats and adds it to the specified EncounterPanel.
 *
 * @example
 * {{{
 * val roboBall = new RoboBall(panel)
 * val attack = roboBall.attack()
 * println(s"The attack of the roboBall is $attack")
 * }}}
 *
 * @author [[https://github.com/wobew/ Roberto Rivera C.]]
 */
class RoboBall(panel: EncounterPanel) extends AbstractWild(3, -1, 1, -1, panel)  {
  stars += 2

  override def respawn(): Unit = {
    new RoboBall(panel)
  }
}