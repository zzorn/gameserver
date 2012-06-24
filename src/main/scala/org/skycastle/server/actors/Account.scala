package org.skycastle.server.actors

import akka.actor.{Props, ActorRef, Actor}
import org.skycastle.server.utils.StringUtils
import java.awt.Color

/**
 *
 */
class Account(val accountName: String) extends Actor {

  private var nextFreeAvatarId = 1

  protected def receive = {
    case c: CreateAvatar =>
      // TODO: Ensure character name is unique on server?  If we want to use name as unique tag.

      // Create the avatar
      val avatar = context.actorOf(Props(new Avatar(c, self)), nextAvatarId)

      // Add avatar to the world
      context.actorFor("/game/world") ! EnterAvatar(avatar)

      // Notify caller that the avatar has been created
      sender ! AvatarCreated(avatar)
  }


  private def nextAvatarId: String = {
    val avatarId = "avatar" + nextFreeAvatarId
    nextFreeAvatarId += 1
    avatarId
  }
}


case class CreateAvatar(name: String, hairColor: Color)

case class AvatarCreated(avatar: ActorRef)