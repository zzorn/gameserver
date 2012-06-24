package org.skycastle.server.actors

import akka.actor.ActorRef
import org.skycastle.server.utils.Vec3

/**
 * An actor located somewhere in the world
 */
trait Located {

  def location: Location


}