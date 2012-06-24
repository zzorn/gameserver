package org.skycastle.server.actors

/**
 * Describes some error or invalid arguments state.
 */
case class ErrorMessage(commandName: String, message: String) {

}