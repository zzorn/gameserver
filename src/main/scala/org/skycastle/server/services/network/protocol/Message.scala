package org.skycastle.server.services.network.protocol

import org.skycastle.server.utils.ParameterChecker

/**
 * A message sent over the network.
 *
 * @param action the action or perception the message is invoking.  Should be a valid identifier.
 * @param parameters parameters, or an empty map for no parameters.
 */
case class Message(action: Symbol, parameters: Map[Symbol, Any]) {
  ParameterChecker.requireIsIdentifier(action, 'action)

  override def toString = "Message " + action.name + " {" + parameters.mkString(", ") + "}"
}