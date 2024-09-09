package cl.uchile.dcc.citric
package model.panel

import model.unit.GameUnit
import controller.GameController
import model.unit.player.PlayerCharacter
import model.unit.wild.{Chicken, RoboBall, Seagull, WildUnit}

import scala.collection.mutable.ArrayBuffer

/** EncounterPanel is a concrete implementation of an AbstractPanel.
 *
 * This panel specializes in managing encounters with wild units.
 *
 * @constructor Creates an EncounterPanel with an empty list of wild units.
 *
 * @example
 * {{{
 * val panel = new EncounterPanel()
 * panel is an empty Encounter Panel
 * }}}
 *
 * @author [[https://github.com/wobew Roberto Rivera C.]]
 */
class EncounterPanel() extends AbstractPanel {

  /**
   * A mutable ArrayBuffer to hold wild units that can be encountered on this panel.
   */
  val wilds: ArrayBuffer[WildUnit] = ArrayBuffer.empty[WildUnit]

  new Chicken(this)
  new RoboBall(this)
  new Seagull(this)

  /**
   * Adds a wild unit to the list of wild units that can be encountered on this panel.
   *
   * @param wild The GameUnit to add.
   */
  def addWild(wild: WildUnit): Unit = {
    wilds += wild
  }

  /**
   * Removes a wild unit from the list of wild units that can be encountered on this panel.
   *
   * @param wild The GameUnit to remove.
   */
  def removeWild(wild: WildUnit): Unit = {
    wilds -= wild
  }

  def name: String = {
    "Encounter Panel"
  }

  /**
   * Starts a combat between the player and a random Wild Unit
   *
   * @param playerCharacter The PlayerCharacter for whom the panel needs to be activated.
   * @param _context the context associated whit the MVC design pattern
   * @param input the response of the player due the wild attack
   */
  override def apply(playerCharacter: PlayerCharacter, _context: Option[GameController], input: String): Unit = {
    val context = _context.get
    val roll = playerCharacter.rollDice()
    if(roll % 3 == 0) context.doAttackWild(wilds(0), input)
    if(roll % 3 == 1) context.doAttackWild(wilds(1), input)
    if(roll % 3 == 2) context.doAttackWild(wilds(2), input)
  }
}
