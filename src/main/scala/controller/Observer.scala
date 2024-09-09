package cl.uchile.dcc.citric
package controller

import model.unit.player.PlayerCharacter


/**
 * The `Observer` trait defines the essential functionality of an observer in the Observer design pattern.
 * It is used to receive updates from the subject (in this context, a `PlayerCharacter`) to which it is attached.
 * This trait is part of a one-to-many dependency between subjects and observers, allowing for automatic
 * updates to observers when the subject's state changes.
 *
 * Implementing this trait in a class allows it to react to changes in the game's state, particularly changes
 * in a player character's status. This is crucial for maintaining synchronized state across different components
 * of the application and for implementing responsive game mechanics.
 */

trait Observer {

  /**
   * Updates the observer with changes from the observable subject. This method is called whenever the subject
   * (in this case, a `PlayerCharacter`) to which this observer is attached, undergoes a change in its state.
   *
   * The method should define how the observer responds to the subject's state change. This can include actions
   * like updating the user interface, changing internal state, or triggering other game mechanics in response.
   *
   * @param observable The `PlayerCharacter` instance that has undergone changes and is notifying this observer.
   */
  def update(observable: PlayerCharacter): Unit
}
