package cl.uchile.dcc.citric
package model.unit.wild

import model.panel.EncounterPanel
import model.unit.{AbstractUnit, GameUnit}
import model.unit.player.PlayerCharacter
import exceptions.InvalidFightException

/**
 * An abstract class representing a wild unit in the game. Wild units are non-player characters
 * that can be encountered on specific panels, known as EncounterPanels. These units have their
 * own attributes such as HP, attack, defense, and evasion and can engage in combat with player
 * characters.
 *
 * @param _maxHp The maximum health points a wild unit can have. It represents the wild unit's endurance.
 * @param _attack The wild unit's capability to deal damage to opponents.
 * @param _defense The wild unit's capability to resist or mitigate damage from opponents.
 * @param _evasion The wild unit's skill to completely avoid certain attacks.
 * @param _panel The EncounterPanel this wild unit is associated with.
 * @constructor Creates an AbstractWild unit and adds it to the specified EncounterPanel.
 */
abstract class AbstractWild(_maxHp: Int,
                            _attack: Int,
                            _defense: Int,
                            _evasion: Int,
                            private val _panel: EncounterPanel)
  extends AbstractUnit(_maxHp, _attack, _defense, _evasion) with WildUnit {

  // Add this wild unit to the EncounterPanel upon creation.
  panel.addWild(this)

  /**
   * Gets the EncounterPanel this wild unit is associated with.
   *
   * @return The EncounterPanel instance.
   */
  def panel: EncounterPanel = _panel

  /**
   * Initiates an attack on another game unit. This wild unit rolls a dice to determine the
   * additional attack value and then calls the appropriate attack method on the enemy game unit.
   *
   * @param enemy The game unit being attacked.
   */
  def doAttack(enemy: GameUnit, input: String = ""): Unit = {
    if (hp == 0) return
    val atk = attack + rollDice()
    enemy.attackWild(this, atk)
  }

  /**
   * Responds to an attack by a player character. This wild unit rolls a dice to randomly decide
   * whether to defend or evade the attack. If the wild unit's HP drops to 0 as a result of the
   * attack, it is removed from the EncounterPanel, and the attacking player character receives
   * victory points and stars.
   *
   * @param enemyPlayer The player character initiating the attack.
   * @param atk         The total attack value calculated for the attacking player.
   * @param input       Optional input to simulate the evasion or defense choice. This is ignored in wild units.
   */
  def attackPlayer(enemyPlayer: PlayerCharacter, atk: Int, input: String = ""): Unit = {
    if (hp == 0) return
    if (rollDice() % 2 == 0) defend(atk) else evade(atk)
    if (hp == 0) {
      enemyPlayer.addVictories(1)
      enemyPlayer.addStars(stars)
      stars -= stars
    }
  }

  /**
   * Wild units do not attack other wild units; this method is intentionally left blank.
   *
   * @param enemyWild Another wild unit.
   * @param atk       The attack value, which is ignored in this context.
   * @param input     Optional input, which is ignored in this context.
   */

  def attackWild(enemyWild: WildUnit, atk: Int, input: String = ""): Unit = {
    throw new InvalidFightException()
  }

  /**
   * Removes this wild unit from its EncounterPanel.
   *
   * @example
   * {{{
   * val chicken  = New Chicken(panel)
   * chicken.remove()
   * chicken is not now in its Encounter Panel
   * }}}
   *
   * @see EncounterPanel
   */
  def remove(): Unit = {panel.removeWild(this)}

  /**
   * Processes the reception of damage by this wild unit and removes it from the EncounterPanel
   * if its HP drops to 0.
   *
   * @param dmg The amount of damage to be applied.
   */
  def takeDamage(dmg: Int): Unit = {
    hp = math.max(hp - dmg, 0)
    if (hp == 0) remove()
    respawn()
  }

  /**
   *  A method to be re-written by each Wild creating a new instance of its class, a "Respawn" to be
   *  stored in the same panel as the original is removed
   */
  def respawn(): Unit
}