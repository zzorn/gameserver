package org.skycastle.server.services.network

import org.apache.mina.core.session.IoSession
import org.skycastle.server.registry.Registry
import org.skycastle.server.models.EntityId
import org.skycastle.server.models.entity.Entity
import org.skycastle.server.utils.Logging
import org.skycastle.server.services.network.protocol.Message

/**
 *
 */
class GameSession(registry:  Registry, networkSession: IoSession, accountName: String, val controlledEntity: EntityId) extends Logging {

  def getId: Long = networkSession.getId

  def handleMessage(message: Message) {
    // DEBUG:
    println("Message "+getId + ": "+message)

    // Send the message to the active character
    val entity: Entity = registry.storageService.getEntity(controlledEntity)
    if (entity != null) {
      // NOTE: The abilities in the entity are responsible for saving the entity if they modify it
      val handled = entity.handleMessage(registry, message)
      if (!handled) log.debug("Unhandled message of type '"+message.action+"' for entity " + entity.entityId)
    }
  }

  def handleLoggedIn() {
    if (controlledEntity != null) {
      val entity = registry.storageService.getEntity(controlledEntity)
      if (entity != null) {
        entity.onControllingSessionChanged(registry, this)
      }
    }
  }

  def handleSessionClosed() {
    if (controlledEntity != null) {
      val entity = registry.storageService.getEntity(controlledEntity)
      if (entity != null) {
        entity.onControllingSessionChanged(registry, null)
      }
    }
  }


  def sendMessage(message: Message) {
    networkSession.write(message)
  }


}