package cl.uchile.dcc.citric
package model.panel

import cl.uchile.dcc.citric.controller.GameController
import cl.uchile.dcc.citric.model.unit.player.PlayerCharacter

import scala.collection.mutable.ArrayBuffer

/** Represents a single cell on a board, known as a Panel.
 *
 * Each panel has its own effect, which can be applied to a character.
 * In the context of the board game '99.7% Citric Liquid', a panel represents a tile or space that a character lands on
 * and experiences an effect, such as losing stars, fighting an enemy, etc. Panels can also be connected to other panels,
 * allowing for the formation of complex board structures and paths that players can navigate.
 *
 * @author [[https://github.com/r8vnhill Ignacio Slater M.]]
 * @author [[https://github.com/wobew Roberto Rivera C.]]
 */
trait Panel {

  /** Retrieves the characters currently positioned on this panel.
   *
   * In the game, multiple characters might occupy the same panel simultaneously, such as when multiple players
   * land on the same space during their moves.
   *
   * @return a List of PlayerCharacter instances on this panel.
   */
  def characters: List[PlayerCharacter]

  /** Retrieves the list of panels that are directly reachable from this panel.
   *
   * This represents the possible next steps a player might take after being on this panel, offering multiple routes or
   * paths in the game.
   *
   * @return a List of Panel instances that are adjacent or connected to this panel.
   */
  def nextPanels: List[Panel]

  /** Adds a character to the panel.
   *
   * This action is typically invoked when a player lands on this panel or if they start their turn on it.
   *
   * @param player The player character to add to the panel.
   */
  def addCharacter(player: PlayerCharacter): Unit

  /** Removes a character from the panel.
   *
   * This action is typically invoked when a player moves away from this panel.
   *
   * @param player The player character to remove from the panel.
   */
  def removeCharacter(player: PlayerCharacter): Unit

  /** Adds a panel to the list of directly connected panels.
   *
   * This method is used to dynamically alter the board structure by adding new connections between panels.
   *
   * @param panel The panel to add as a connection.
   */
  def addPanel(panel: Panel): Unit

  /** Removes a panel from the list of directly connected panels.
   *
   * This method is used to dynamically alter the board structure by removing existing connections between panels.
   *
   * @param panel The panel to remove from the connections.
   */
  def removePanel(panel: Panel): Unit

  /** Activates the panel's effect on the given player character.
   *
   * Each panel subtype will implement this method to enact its unique effect on the character, such as triggering a battle,
   * granting stars, or other game-specific actions.
   *
   * @param playerCharacter The PlayerCharacter on which to apply the panel's effect.
   * @param _context the context associated with whit the MVC design pattern, only given if needed
   * @param input an input required to simulate the combat between the player and wilds, only given when needed
   */
  def apply(playerCharacter: PlayerCharacter, _context: Option[GameController], input: String): Unit

  /**
   * Returns the name of the panel for a correct visualization by the final user
   * @return the type of the actual panel
   */
  def name: String
}
