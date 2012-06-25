package org.skycastle.server.services.actor

import org.skycastle.server.services.Service
import akka.actor.ActorRef

/**
 * Service for setting up the root actor.
 */
trait ActorService extends Service {


  def getAccountActor(accountName: String): ActorRef


  def sendMessage(actorRef: ActorRef, message: Any)

}