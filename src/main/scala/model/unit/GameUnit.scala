package cl.uchile.dcc.citric
package model.unit

import model.unit.player.PlayerCharacter
import model.unit.wild.WildUnit

import scala.util.Random

/**
 * Trait defining the basic attributes and functionalities of a unit in the game '99.7% Citric Liquid'.
 * This serves as a base trait for various types of units, like player characters and wild units,
 * providing a common interface for their interactions and attributes.
 *
 * A GameUnit is characterized by its health points (HP), attack, defense, and evasion stats,
 * which govern how it performs in combat situations.
 *
 * @author [[https://github.com/wobew Roberto Rivera C.]]
 */
trait GameUnit {

  /** The maximum Hit Points (HP) this unit can have, determining its survivability. */
  def maxHp: Int

  /** The current Hit Points (HP) this unit has, representing its remaining life. */
  def hp: Int

  /** The attack power of this unit, influencing the damage it can deal to others. */
  def attack: Int

  /** The defense power of this unit, influencing how much damage it can reduce from incoming attacks. */
  def defense: Int

  /** The evasion capability of this unit, affecting its ability to dodge attacks. */
  def evasion: Int

  /** The current number of stars this unit has. */
  def stars: Int

  /** Attempts to defend against an attack, reducing damage based on the unit's defense stat. */
  def defend(dmg: Int): Unit

  /** Attempts to evade an attack, potentially avoiding all damage based on the unit's evasion stat. */
  def evade(dmg: Int): Unit

  /** Random number generator for simulating dice rolls and other random events within the game. */
  protected val randomNumberGenerator: Random

  /** Simulates a dice roll, commonly used for determining movement or outcomes in combat. */
  def rollDice(): Int

  /** Initiates an attack sequence against another GameUnit. */
  def doAttack(enemy: GameUnit, input: String): Unit

  /** This method is called by double dispatch when a Unit is attacked by a PlayerCharacter*/
  def attackPlayer(enemyPlayer: PlayerCharacter, atk: Int, input: String = ""): Unit

  /** This method is called by double dispatch when a Unit is attacked by a WildUnit*/
  def attackWild(enemyWild: WildUnit, atk: Int, input: String = ""): Unit

  /** Processes the reception of damage by this unit, subtracting it from its current HP. */
  def takeDamage(dmg: Int): Unit

  /** Calls the setter method to add a quantity of stars */
  def addStars(plus: Int): Unit

  /** Calls the setter method to remove quantity of stars */
  def removeStars(minus: Int): Unit

  /** Calls the setter method to add a quantity of HP */
  def addHp(plus: Int): Unit

  /** Calls the setter method to remove quantity of HP */
  def removeHp(minus: Int): Unit
}


