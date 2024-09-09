package cl.uchile.dcc.citric
package model.panel

import model.unit.player.PlayerCharacter

import cl.uchile.dcc.citric.controller.GameController

import scala.collection.mutable.ArrayBuffer

/** AbstractPanel is an abstract base class representing a panel in the '99.7% Citric Liquid' game board, which serves as a space where player characters can land and interact.
 *
 * It provides common functionalities for managing player characters and next possible panels.
 *
 * @constructor Creates an abstract panel with empty lists of characters and next panels.
 *
 * @author [[https://github.com/wobew Roberto Rivera C.]]
 */
abstract class AbstractPanel() extends Panel{

  /**
   * A mutable ArrayBuffer holding player characters currently on this panel.
   */
  protected val _characters: ArrayBuffer[PlayerCharacter] = ArrayBuffer.empty[PlayerCharacter]
  def characters: List[PlayerCharacter] = _characters.toList

  /**
   * A mutable ArrayBuffer holding the next possible panels accessible from this panel.
   */
  private val _nextPanels: ArrayBuffer[Panel] = ArrayBuffer.empty[Panel]

  def nextPanels: List[Panel] = _nextPanels.toList

  /**
   * Adds a player character to this panel, allowing them to interact with it.
   *
   * @param player The PlayerCharacter to add.
   */
  def addCharacter(player: PlayerCharacter): Unit = {
    _characters += player
  }

  /**
   * Removes a player character from this panel, ending their interaction with it.
   *
   * @param player The PlayerCharacter to remove.
   */
  def removeCharacter(player: PlayerCharacter): Unit = {
    _characters -= player
  }

  /**
   * Adds a panel to the list of next possible panels, expanding the movement options from this panel.
   *
   * @param panel The Panel to add.
   */
  def addPanel(panel: Panel): Unit = {
    _nextPanels += panel
  }

  /**
   * Removes a panel from the list of next possible panels, limiting the movement options from this panel.
   *
   * @param panel The Panel to remove.
   */
  def removePanel(panel: Panel): Unit = {
    _nextPanels -= panel
  }

  /**
   * An abstract method to activate the panel for a given player character.
   * Subclasses should provide an implementation for this method.
   *
   * @param playerCharacter The PlayerCharacter for whom the panel needs to be activated.
   */
  def apply(playerCharacter: PlayerCharacter, _context: Option[GameController] = None, input: String = ""): Unit = { }

}
