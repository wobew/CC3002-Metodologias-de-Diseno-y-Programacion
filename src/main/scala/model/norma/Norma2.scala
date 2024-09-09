package cl.uchile.dcc.citric
package model.norma

import model.unit.player.PlayerCharacter

/**
 * `Norma2` represents the second Norma level in the game's progression system for player characters.
 * Players at this level need to accumulate a minimum of 30 stars or 3 victory to advance to the next level.
 * When the conditions are met, this class facilitates the upgrade to `Norma3`.
 *
 * @param owner The player character who is at Norma level 2.
 *
 * @constructor Creates a `Norma2` instance with predefined requirements of 30 stars and 3 victory.
 */
class Norma2(owner: PlayerCharacter) extends AbstractNorma(30, 3, owner) {
  override def upgrade(): Unit = {
    if (owner.stars >= stars || owner.victories >= victories) {
      owner.addNorma(1)
      owner.normaClass = new Norma3(owner)
      owner.normaClass.upgrade()
    }

  }
}
