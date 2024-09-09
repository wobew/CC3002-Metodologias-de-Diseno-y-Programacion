package cl.uchile.dcc.citric
package model.norma

import model.unit.player.PlayerCharacter

/**
 * `Norma3` represents the third Norma level in the game's progression system for player characters.
 * Players at this level need to accumulate a minimum of 70 stars or 6 victory to advance to the next level.
 * When the conditions are met, this class facilitates the upgrade to `Norma4`.
 *
 * @param owner The player character who is at Norma level 3.
 *
 * @constructor Creates a `Norma3` instance with predefined requirements of 70 stars and 6 victory.
 */
class Norma3(owner: PlayerCharacter) extends AbstractNorma(70, 6, owner) {
  override def upgrade(): Unit = {
    if (owner.stars >= stars || owner.victories >= victories) {
      owner.addNorma(1)
      owner.normaClass = new Norma4(owner)
      owner.normaClass.upgrade()
    }
  }
}
