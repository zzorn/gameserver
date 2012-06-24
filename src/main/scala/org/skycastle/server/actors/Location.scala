package org.skycastle.server.actors

import akka.actor.ActorRef
import org.skycastle.server.utils.Vec3

/**
 * Represents a location in the world.
 * Note that this class is mutable, so if it is send as a message, make a copy of the location to send, and call set to
 * change a target location.
 */
case class Location(var space: ActorRef = null,
                    pos: Vec3 = Vec3(),
                    dir: Vec3 = Vec3()) {

  def set(other: Location) {
    space = other.space
    pos.set(other.pos)
    dir.set(other.dir)
  }

  def copy(): Location = {
    val newLoc = Location()
    newLoc.set(this)
    newLoc
  }

}