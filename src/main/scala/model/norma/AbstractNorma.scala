package cl.uchile.dcc.citric
package model.norma

import model.unit.player.PlayerCharacter

/**
 * An abstract class that provides a concrete foundation for the Norma system in the game.
 * It defines the basic attributes needed for a Norma level, including the number of stars
 * and victories required, as well as the owner of the Norma level, which is a player character.
 * The upgrade logic is left unimplemented for subclasses to define specific upgrade behaviors.
 *
 * Upon instantiation, this class sets itself as the Norma level of the owner player character.
 *
 * @param _stars The number of stars required to upgrade to the next Norma level.
 * @param _victories The number of victories required to upgrade to the next Norma level.
 * @param _owner The player character who owns and is subject to this Norma level.
 *
 * @constructor Initializes an abstract Norma level with specified stars, victories, and owner.
 */
abstract class AbstractNorma(private val _stars: Int,
                             private val _victories: Int,
                             private val _owner: PlayerCharacter) extends Norma {

  /** Returns the number of stars required for the Norma level. */
  def stars: Int = _stars

  /** Returns the number of victories required for the Norma level. */
  def victories: Int = _victories

  /** Returns the owner player character of the Norma level. */
  def owner: PlayerCharacter = _owner

  // Automatically sets this Norma level as the current one for the owner upon creation.
  owner.normaClass = this

  /**
   * The method to upgrade the player character to the next Norma level.
   * This method is abstract and should be implemented by subclasses to define
   * specific upgrade behaviors and conditions.
   */
  def upgrade(): Unit = { /* Implementation to be provided by subclasses */ }
}
