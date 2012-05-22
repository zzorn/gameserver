package org.skycastle.server.services.network

import org.skycastle.server.services.Service
import org.skycastle.server.models.EntityId
import protocol.Message

/**
 *
 */
trait NetworkService extends Service {

  /**
   * Sends a message to a session with the specified id, and associated with the specified sender.
   * @return true if message delivered (or delivery started), false if the session has expired or is no longer associated
   *         with the specified sender (in this case the caller can stop any future calls with that session and sender).
   */
  def sendMessage(sender: EntityId, session: Long, message: Message): Boolean

}