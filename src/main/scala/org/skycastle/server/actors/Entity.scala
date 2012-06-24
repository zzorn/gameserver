package org.skycastle.server.actors

import akka.actor.{ActorRef, Actor}
import org.skycastle.server.utils.Vec3

/**
 *
 */
class Entity extends Actor with Located {
  val location = Location()

  protected def receive = {
    case msg: SetLocation => {

      val oldSpace: ActorRef = location.space

      location.set(msg.location)

      if (oldSpace != msg.location.space) {
        // Notify spaces about us
        if (oldSpace != null) oldSpace ! RemoveEntity(self)
        if (location.space != null) location.space ! AddEntity(self)
      }

    }
  }

}

case class SetLocation(location: Location)

