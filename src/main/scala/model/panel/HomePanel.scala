package cl.uchile.dcc.citric
package model.panel

import model.unit.player.PlayerCharacter

import cl.uchile.dcc.citric.controller.GameController

/**
 * HomePanel is a concrete implementation of an AbstractPanel.
 * It specializes in serving as a home base for a specific player character, typically providing
 * recovery and checkpoint functionalities in the game '99.7% Citric Liquid'.
 *
 * @param owner The PlayerCharacter who owns this home panel.
 * @constructor Creates a HomePanel and sets the owner as present on it.
 * @example
 * {{{
 * val panel = new HomePanel(player)
 * // `panel` is a HomePanel initialized with its owner present.
 * }}}
 * @author [[https://github.com/wobew Roberto Rivera C.]]
 */
class HomePanel(val owner: PlayerCharacter) extends AbstractPanel {

  /**
   * Flag to indicate whether the owner is currently on this home panel.
   */
  private var _ownerIn: Boolean = false

  /**
   * Public getter for the _ownerIn flag.
   * @return Boolean status of the owner's presence.
   */
  def ownerIn: Boolean = _ownerIn

  /**
   * Public setter for the _ownerIn flag, updating the owner's presence status.
   * @param newOwnerIn_ Boolean value to update the owner's presence status.
   */
  def ownerIn_=(newOwnerIn_ : Boolean): Unit = {
    _ownerIn = newOwnerIn_
  }

  // Add the owner to the home panel upon creation.
  addCharacter(owner)

  /**
   * Overrides the addCharacter method from AbstractPanel to handle the owner's presence.
   * When the owner is added, it sets the owner as present on this panel and updates their home status.
   * @param player The PlayerCharacter to add to this home panel.
   */
  override def addCharacter(player: PlayerCharacter): Unit = {
    super.addCharacter(player)
    if (player == owner) {
      ownerIn = true
      player.inHome = true
    }
  }

  /**
   * Overrides the removeCharacter method from AbstractPanel to handle the owner's absence.
   * When the owner is removed, it sets the owner as not present on this panel and updates their home status.
   * @param player The PlayerCharacter to remove from this home panel.
   */
  override def removeCharacter(player: PlayerCharacter): Unit = {
    super.removeCharacter(player)
    if (player == owner) {
      ownerIn = false
      player.inHome = false
    }
  }

  /**
   * Activates the home panel functionality for a given player character.
   * If the player character is the owner and their HP is below maximum, it increments their HP by 1.
   * @param player The PlayerCharacter to activate the home panel for.
   */
  def activate(player: PlayerCharacter): Unit = {
    if(!characters.contains(player)) return
    if (player.hp < player.maxHp) {
      player.addHp(1)
    }
  }

  /**
   * Performs a Norma check for the given player character, if and only if the player is in the panel
   * the normaClass of the character takes care of the conditions by it's upgrade method
   * @param player The PlayerCharacter to perform the Norma check for.
   */
  def normaCheck(player: PlayerCharacter): Unit = {
    if(!characters.contains(player)) return
    player.normaClass.upgrade()
  }

  def name: String = {
    "Home Panel"
  }

  /**
   * Does the previous effects
   *
   * @param player the player the effects is applied on
   * @param _context the context associated with whit the MVC design pattern, only given if needed
   * @param input an input required to simulate the combat between the player and wilds, only given when needed
   */
  override def apply(player: PlayerCharacter, _context: Option[GameController] = None, input: String = ""): Unit = {
    activate(player)
    normaCheck(player)
  }

}
