package org.skycastle.server.actors

import akka.actor.{Props, ActorRef, Actor}
import org.skycastle.server.utils.StringUtils
import java.awt.Color

/**
 *
 */
class Account(val accountName: String) extends Actor {

  private var nextFreeAvatarId = 1
  private var controllingSessionId = 0L

  protected def receive = {
    case c: CreateAvatar => {
      // TODO: Ensure character name is unique on server?  If we want to use name as unique tag.

      // Create the avatar
      val avatar = context.actorOf(Props(new Avatar(c, self)), nextAvatarId)

      // Add avatar to the world
      context.actorFor("/game/world") ! EnterAvatar(avatar)

      // Notify caller that the avatar has been created
      sender ! AvatarCreated(avatar)
    }

    case msg: ControlSessionConnected => {
      controllingSessionId = msg.sessionId
    }

    case msg: ControlSessionDisconnected => {
      if (controllingSessionId == msg.sessionId) {
        // TODO: Remove controlled character from world?
        controllingSessionId = 0
      }
    }

    case GetAccountData => {
      // TODO: Send to session
      // TODO: Should session be an actor?
      asdf
    }
  }


  private def nextAvatarId: String = {
    val avatarId = "avatar" + nextFreeAvatarId
    nextFreeAvatarId += 1
    avatarId
  }
}


case class CreateAvatar(name: String, hairColor: Color)

case class AvatarCreated(avatar: ActorRef)

case class ControlSessionConnected(sessionId: Long)

case class ControlSessionDisconnected(sessionId: Long)

case object GetAccountData

case class AccountData(accountName: String, account: ActorRef, characters: List[ActorRef])
