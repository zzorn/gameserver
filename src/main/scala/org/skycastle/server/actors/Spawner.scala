package org.skycastle.server.actors

import akka.actor.{ActorRef, Actor}
import org.skycastle.server.utils.Vec3

/**
 *
 */
case class Spawner() extends Entity {

  override protected def receive = super.receive.orElse( {
    case msg: SpawnEntity => {

      // Set entity at the spawn position
      msg.entity ! location.copy()

      // TODO: Show spawn effect at the location

    }
  })

}

case class SpawnEntity(entity: ActorRef)