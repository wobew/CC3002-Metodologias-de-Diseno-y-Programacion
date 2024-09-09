package cl.uchile.dcc.citric
package controller

/**
 * The `Subject` trait defines the core functionalities of a subject in the Observer design pattern.
 * It provides the ability to attach and notify observers. In the context of this application,
 * a subject can be any game entity whose state changes are of interest and should be communicated
 * to other parts of the application (observers).
 *
 * Implementing this trait allows an object to have one-to-many dependency relationships with other objects
 * (observers), such that when the state of the subject changes, all its dependents are notified and updated automatically.
 * This pattern is widely used for creating an efficient and modular framework for state monitoring and event handling.
 */
trait Subject {

  /**
   * Adds an observer to this subject. The observer will be notified of any state changes in this subject.
   * This method is used to register an observer that is interested in being informed about state updates.
   *
   * @param observer The observer object that wants to be added.
   */
  def addObserver(observer: Observer): Unit

  /**
   * Notifies all registered observers of a change in the subject's state.
   * This method is typically called whenever the state of the subject changes in a significant way.
   * It allows for the propagation of state change information to all interested observers.
   */
  def notifyObservers(): Unit
}
