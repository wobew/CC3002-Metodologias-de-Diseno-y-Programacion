package cl.uchile.dcc.citric
package model.norma

import model.unit.player.PlayerCharacter

/**
 * The `Norma` trait defines the progression or leveling system for player characters in the game.
 * It encapsulates the conditions under which a player character can achieve a new Norma level,
 * which often involves collecting a certain number of stars or achieving a certain number of victories.
 *
 * Implementations of this trait will provide the specific logic for upgrading a player's Norma level,
 * which can affect the player's abilities or status within the game.
 *
 * @note A player's progression through the Norma levels is a key component of the game's victory conditions.
 *
 * @example
 * {{{
 *   if (player.stars >= norma.stars && player.victories >= norma.victories) {
 *     norma.upgrade()
 *   }
 * }}}
 */
trait Norma {

  /**
   * The number of stars required for the next Norma level.
   */
  def stars: Int

  /**
   * The number of victories required for the next Norma level.
   */
  def victories: Int

  /**
   * The player character who owns this Norma.
   */
  def owner: PlayerCharacter

  /**
   * Upgrades the Norma level of the owning player character. Implementations will define
   * the specific effects and conditions of the upgrade, such as increased stats or new abilities.
   */
  def upgrade(): Unit
}

