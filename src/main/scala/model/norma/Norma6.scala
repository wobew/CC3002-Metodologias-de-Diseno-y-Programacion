package cl.uchile.dcc.citric
package model.norma

import model.unit.player.PlayerCharacter

/**
 * `Norma6` represents the sixth and last Norma level in the game's progression system for player characters.
 * Players at this level can't progress to a next level which is represented with high stars and victories
 * requirements and a blank upgrade method
 *
 * @param owner The player character who is at Norma level 6.
 *
 * @constructor Creates a `Norma5` instance with predefined requirements of 999 stars and 999 victory.
 */
class Norma6(owner: PlayerCharacter) extends AbstractNorma(999, 999, owner){

}