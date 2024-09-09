package cl.uchile.dcc.citric
package model.norma

import model.unit.player.PlayerCharacter

/**
 * `Norma1` represents the first Norma level in the game's progression system for player characters.
 * Players at this level need to accumulate a minimum of 10 stars or 1 victory to advance to the next level.
 * When the conditions are met, this class facilitates the upgrade to `Norma2`.
 *
 * @param owner The player character who is at Norma level 1.
 *
 * @constructor Creates a `Norma1` instance with predefined requirements of 10 stars and 1 victory.
 */
class Norma1(owner: PlayerCharacter) extends AbstractNorma(10, 1, owner) {
  /**
   * Upgrades the owner player character to `Norma2` if they have met the requirements.
   * If the player has at least 10 stars or 1 victory, their Norma level is set to 2, and
   * an instance of `Norma2` is assigned as their new Norma class. The upgrade method of
   * `Norma2` is then called to check if any further immediate upgrades are applicable.
   */
  override def upgrade(): Unit = {
    if(owner.stars >= stars || owner.victories >= victories) {
      owner.addNorma(1)
      owner.normaClass = new Norma2(owner)
      // Call upgrade on the new Norma in case further immediate upgrades are applicable.
      owner.normaClass.upgrade()
    }
  }
}