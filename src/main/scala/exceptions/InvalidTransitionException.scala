package cl.uchile.dcc.citric
package exceptions

class InvalidTransitionException(message: String) extends Exception(s"Invalid transition - $message - for this state!") {

}
