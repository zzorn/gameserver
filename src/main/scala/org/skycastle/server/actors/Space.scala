package org.skycastle.server.actors

import akka.actor.{ActorRef, Actor}
import java.util

/**
 *
 */
class Space extends Actor {

  private val entities = new util.ArrayList[ActorRef]()

  protected def receive = {
    case msg: AddEntity => {
      entities.add(msg.entity)
    }
    case msg: RemoveEntity => {
      entities.remove(msg.entity)
    }
  }
}

case class AddEntity(entity: ActorRef)
case class RemoveEntity(entity: ActorRef)
