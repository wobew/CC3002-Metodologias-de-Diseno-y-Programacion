package cl.uchile.dcc.citric
package controller.states

import controller.GameController

/**
 * The `GameState` trait defines the essential functionalities and checks for different states in a game.
 * It encapsulates the various actions and conditions that can occur during a game, such as rolling dice,
 * starting a new chapter, playing a turn, and responding to different game events. Implementing this trait
 * allows for managing the game's flow and handling state-specific actions and transitions.
 *
 * Each method in this trait represents an action that can be performed or a condition to be checked in the
 * game's current state. This design allows for a flexible and modular approach to handle game logic and transitions.
 */
trait GameState {

  /**
   * A getter method for the controller associated with the current game state
   * @return the controller
   */
  def controller: GameController

  /**
   * A setter method to associate a controller to this game state
   * @param cont the controller to be associated
   */
  def controller_=(cont: GameController): Unit

  /** Methods representing different game actions that allows transition between game states
   * more information about them in the README. The inputs are simulating the players input
   */
  def startGame(): Unit
  def rollDice(): Unit
  def doEffect(input: String): Unit
  def newChapter(): Unit
  def playTurn(): Unit
  def stopMovement(): Unit
  def endCombat(): Unit
  def normaSix(): Unit
  def knockedOut(): Unit
  def choosePath(input: Int): Unit
  def outOfMovements(): Unit
  def attack(input: Int): Unit
  def recoveryRoll(): Unit
  def respond(input: Int): Unit

  /** Methods representing state checks, more information about them in the README
   *
   * @return Boolean representing if the state is of that type
   */
  def isChapter: Boolean
  def isCombat: Boolean
  def isEnd: Boolean
  def isLanding: Boolean
  def isMoving: Boolean
  def isTurn: Boolean
  def isRecovery: Boolean
  def isStart: Boolean
  def isWait: Boolean

}

