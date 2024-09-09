package cl.uchile.dcc.citric
package model.unit.player

import model.unit.GameUnit
import model.norma.Norma

/**
 * PlayerUnit extends GameUnit to incorporate additional properties and behaviors specific to player characters in the '99.7% Citric Liquid' game.
 *
 * This trait encapsulates characteristics like the player's name, norma level, and status indicators for being at home or knocked out, among others.
 * It also integrates the Norma system, which dictates the conditions for a player's level advancement within the game.
 *
 * @note This serves as a base trait for player characters and should be extended by specific player classes.
 * @author [[https://github.com/wobew Roberto Rivera C.]]
 */
trait PlayerUnit extends GameUnit {

  /**
   * The name of the player unit, providing an identity within the game context.
   *
   * @return The name of this player unit.
   */
  def name: String

  /**
   * The Norma object associated with this player unit, which determines the conditions
   * and progression for Norma levels.
   *
   * @return The Norma class instance tied to this player unit.
   */
  def normaClass: Norma

  /**
   * A flag indicating whether this player unit is currently on its home panel,
   * which can influence gameplay mechanics like healing or Norma checks.
   *
   * @return True if the player unit is on its home panel, false otherwise.
   */
  def inHome: Boolean

  /**
   * A flag indicating whether this player unit has been knocked out,
   * impacting its ability to take actions until revived.
   *
   * @return True if the player unit is knocked out, false otherwise.
   */
  def KO: Boolean

  /**
   * The current Norma level of this player unit, which represents the progression
   * towards the game's victory conditions.
   *
   * @return The current Norma level of the player unit.
   */
  def norma: Int

  /**
   * The current number of victories this player unit has achieved, contributing to
   * Norma progression and overall scoring.
   *
   * @return The number of victories of the player unit.
   */
  def victories: Int

  /** Calls the setter method to add a quantity of victories */
  def addVictories(plus: Int): Unit

  /** Calls the setter method to remove a quantity of victories   */
  def removeVictories(minus: Int): Unit

  /** Calls the setter method to add a norma level */
  def addNorma(plus: Int): Unit

  // Implementations of the trait's methods should be provided within the classes that extend this trait.
}

