package org.skycastle.server.actors
import akka.actor.{Props, ActorRef, Actor}

/**
 * A game character.
 */
class Avatar(avatarParameters: CreateAvatar, val account: ActorRef) extends Entity {

  override def preStart() {
    // Do any initialization based on the creation parameters
  }


}