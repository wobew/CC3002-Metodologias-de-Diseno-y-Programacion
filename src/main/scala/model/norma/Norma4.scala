package cl.uchile.dcc.citric
package model.norma

import model.unit.player.PlayerCharacter

/**
 * `Norma4` represents the fourth Norma level in the game's progression system for player characters.
 * Players at this level need to accumulate a minimum of 120 stars or 10 victory to advance to the next level.
 * When the conditions are met, this class facilitates the upgrade to `Norma5`.
 *
 * @param owner The player character who is at Norma level 4.
 *
 * @constructor Creates a `Norma4` instance with predefined requirements of 120 stars and 10 victory.
 */
class Norma4(owner: PlayerCharacter) extends AbstractNorma(120, 10, owner){
  override def upgrade(): Unit = {
    if (owner.stars >= stars || owner.victories >= victories) {
      owner.addNorma(1)
      owner.normaClass = new Norma5(owner)
      owner.normaClass.upgrade()
    }
  }
}
