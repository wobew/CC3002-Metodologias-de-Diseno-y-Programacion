package cl.uchile.dcc.citric
package model.unit

import scala.util.Random

/**
 * AbstractUnit provides a skeletal implementation of the GameUnit interface to minimize the effort required to implement this interface.
 *
 * This abstract class encapsulates the primary attributes and behaviors of units in the game, such as their health points,
 * attack, defense, and evasion capabilities, along with their interactions during the game's combat phase.
 *
 * @param _maxHp The maximum health points a unit can have.
 * @param _attack The base attack value for a unit.
 * @param _defense The base defense value for a unit.
 * @param _evasion The base evasion value for a unit.
 * @param randomNumberGenerator A Random object used for simulating dice rolls within the unit's methods.
 *                              Defaults to a new Random instance if not provided.
 * @constructor Initializes a unit with specified attributes and a Random object for dice rolls.
 */
abstract class AbstractUnit(protected val _maxHp: Int,
                            protected val _attack: Int,
                            protected val _defense: Int,
                            protected val _evasion: Int,
                            protected val randomNumberGenerator: Random = new Random()) extends GameUnit {

  /** The unit's maximum HP, which sets the upper limit for health. */
  def maxHp: Int = _maxHp

  /** The unit's attack strength, which determines the base damage it can inflict. */
  def attack: Int = _attack

  /** The unit's defense, which is used to mitigate incoming damage. */
  def defense: Int = _defense

  /** The unit's evasion, which helps it avoid attacks. */
  def evasion: Int = _evasion

  /** The current HP of the unit, which when depleted, will typically result in the unit's defeat. */
  private var _hp = maxHp
  def hp: Int = _hp

  /** Sets the current HP of the unit, ensuring it doesn't fall below 0 or exceed maxHp. */
  protected def hp_=(newHp: Int): Unit = {
    _hp = newHp
    if (_hp < 0) _hp = 0
    if (_hp > maxHp) _hp = maxHp
  }

  /**
   * Calls the setter method to add a quantity of HP
   *
   * @param plus The HP to be added
   */
  def addHp(plus: Int): Unit = {
    hp += plus
  }

  /**
   * Calls the setter method to remove quantity of HP
   *
   * @param minus The HP to be removed
   */
  def removeHp(minus: Int): Unit = {
    hp -= minus
  }

  /** The number of stars the unit has collected, often used as a scoring mechanism. */
  private var _stars = 0
  def stars: Int = _stars

  /** Sets the number of stars the unit has, ensuring the count doesn't fall below 0. */
  protected def stars_=(newStars: Int): Unit = {
    _stars = newStars
    if (_stars < 0) _stars = 0
  }

  /**
   * Calls the setter method to add a quantity of stars
   *
   *  @param plus The stars to be added
   */
  def addStars(plus: Int): Unit = {
    stars += plus
  }

  /**
   * Calls the setter method to remove quantity of stars
   *
   * @param minus The stars to be removed
   */
  def removeStars(minus: Int): Unit = {
    stars -= minus
  }

  /**
   * Rolls a dice and returns a random value between 1 and 6, representing a typical dice roll.
   *
   * @return The result of the dice roll.
   */
  def rollDice(): Int = randomNumberGenerator.nextInt(6) + 1

  /**
   * Attempts to defend against an incoming attack using a dice roll combined with the unit's defense.
   *
   * The actual damage taken is calculated as the attacker's roll minus the sum of the defender's roll and defense,
   * with a minimum damage of 1.
   *
   * @param atk The attack value from the attacker that the unit must defend against.
   */
  def defend(atk: Int): Unit = {
    val roll = rollDice()
    val dmg = math.max(1, atk - (roll + defense))
    takeDamage(dmg)
  }

  /**
   * Attempts to evade an incoming attack using a dice roll combined with the unit's evasion.
   *
   * If the sum of the evasion roll and the unit's evasion is greater than the attacker's roll, the attack is evaded and no damage is taken.
   * Otherwise, full damage is taken.
   *
   * @param atk The attack value from the attacker that the unit must evade.
   */
  def evade(atk: Int): Unit = {
    val roll = rollDice()
    val dmg = if (roll + evasion > atk) 0 else atk
    takeDamage(dmg)
  }

  // Note: The documentation for the methods attackWild and takeDamage should be included here if they are implemented within this class.
}
