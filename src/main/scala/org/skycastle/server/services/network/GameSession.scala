package org.skycastle.server.services.network

import org.apache.mina.core.session.IoSession
import org.skycastle.server.registry.Registry
import org.skycastle.server.models.EntityId
import org.skycastle.server.models.entity.Entity
import org.skycastle.server.utils.Logging
import org.skycastle.server.services.network.protocol.Message
import akka.actor.ActorRef
import org.skycastle.server.actors.{ControlSessionDisconnected, ControlSessionConnected}

/**
 *
 */
class GameSession(registry:  Registry, networkSession: IoSession, accountName: String, val controlledActor: ActorRef) extends Logging {

  def getId: Long = networkSession.getId

  def handleMessage(message: Message) {
    // DEBUG:
    println("Message "+getId + ": "+message)

    // Send the message to the active character
    registry.actorService.sendMessage(controlledActor, message)
  }

  def handleLoggedIn() {
    registry.actorService.sendMessage(controlledActor, ControlSessionConnected(getId))
  }

  def handleSessionClosed() {
    registry.actorService.sendMessage(controlledActor, ControlSessionDisconnected(getId))
  }

  def sendMessage(message: Message) {
    networkSession.write(message)
  }


}