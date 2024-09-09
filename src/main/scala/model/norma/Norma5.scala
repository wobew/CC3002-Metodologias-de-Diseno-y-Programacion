package cl.uchile.dcc.citric
package model.norma

import model.unit.player.PlayerCharacter

/**
 * `Norma5` represents the fifth Norma level in the game's progression system for player characters.
 * Players at this level need to accumulate a minimum of 200 stars or 14 victory to advance to the next level.
 * When the conditions are met, this class facilitates the upgrade to `Norma5`.
 *
 * @param owner The player character who is at Norma level 5.
 *
 * @constructor Creates a `Norma5` instance with predefined requirements of 120 stars and 10 victory.
 */
class Norma5(owner: PlayerCharacter) extends AbstractNorma(200, 14, owner){

  override def upgrade(): Unit = {
    if (owner.stars >= stars || owner.victories >= victories) {
      owner.addNorma(1)
      owner.normaClass = new Norma6(owner)
      owner.normaClass.upgrade()
    }
  }

}
