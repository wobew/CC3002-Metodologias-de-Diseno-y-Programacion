package cl.uchile.dcc.citric
package controller.states

import controller.GameController
import exceptions.InvalidTransitionException

/**
 * The `AbstractGameState` class provides a skeletal implementation of the `GameState` interface,
 * to minimize the effort required to implement this interface. It serves as a base class for
 * different game states in a game controlled by `GameController`.
 *
 * This abstract class defines default behaviors for each action and state check defined in the `GameState` interface.
 * By default, each action method throws an `InvalidTransitionException`, indicating that the action is not valid
 * for the current state. The state check methods return false.
 *
 * Specific game states should extend this class and override the methods relevant to that state, providing the appropriate
 * logic and conditions for each action and state check.
 */
abstract class AbstractGameState() extends GameState {

  /**
   * The controller associated with this Game Stated, private for security issues
   */

  private var _controller: Option[GameController] = None

  /**
   *  A getter method to the game controller, throwing an assertion error if it is not defined.
   */

  def controller: GameController = {
    if (_controller.isDefined) {
      _controller.get
    } else {
      throw new AssertionError("Controller not defined")
    }
  }

  /**
   * A setter method the game controller for this state.
   *
   * @param cont the game controller
   */

  def controller_=(cont: GameController): Unit = {
    _controller = Some(cont)
  }

  /**
   *  Default implementations of transitions between states, each throwing an InvalidTransitionException.
   */

  def startGame(): Unit = throw new InvalidTransitionException("start game")
  def rollDice(): Unit = throw new InvalidTransitionException("roll dice")
  def doEffect(input: String = ""): Unit = throw new InvalidTransitionException("do effect")
  def newChapter(): Unit  = throw new InvalidTransitionException("new chapter")
  def playTurn(): Unit  = throw new InvalidTransitionException("player turn")
  def stopMovement(): Unit  = throw new InvalidTransitionException("stop movement")
  def endCombat(): Unit  = throw new InvalidTransitionException("end combat")
  def normaSix(): Unit  = throw new InvalidTransitionException("norma 6 reached")
  def knockedOut(): Unit  = throw new InvalidTransitionException("knocked out")
  def choosePath(input: Int): Unit  = throw new InvalidTransitionException("choose path")
  def outOfMovements(): Unit  = throw new InvalidTransitionException("out of movements")
  def attack(input: Int): Unit  = throw new InvalidTransitionException("attack")
  def recoveryRoll(): Unit = throw new InvalidTransitionException("recovery roll")
  def respond(input: Int): Unit = throw new InvalidTransitionException("respond")

  /**
   * Default implementations of state checks, returning false.
   */
  def isChapter: Boolean = false
  def isCombat: Boolean = false
  def isEnd: Boolean = false
  def isLanding: Boolean = false
  def isMoving: Boolean = false
  def isTurn: Boolean = false
  def isRecovery: Boolean = false
  def isStart: Boolean = false
  def isWait: Boolean = false

}

