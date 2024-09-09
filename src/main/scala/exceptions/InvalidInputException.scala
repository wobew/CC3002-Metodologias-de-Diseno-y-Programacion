package cl.uchile.dcc.citric
package exceptions

/**
 * A custom exception that is thrown when an invalid input is encountered.
 * This exception is used to signal that an expected input does not match the
 * required format or allowed values within the game logic.
 *
 * @param input The offending input that triggered the exception.
 * @constructor Creates a new instance of InvalidInputException with a message that includes the invalid input.
 */
class InvalidInputException(input: String) extends Exception(s"An invalid input was found -- $input")