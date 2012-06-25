package org.skycastle.server.actors

import akka.actor.{ActorRef, Props, Actor}
import org.skycastle.server.registry.Registry
import org.skycastle.server.services.authenticator.{AccountCreationError, AccountCreated, AccountCreationResponse}
import org.skycastle.server.models.account.User

/**
 *
 */
class AccountManager(registry: Registry) extends Actor {

  protected def receive = {

    case msg: SetControllingSession => {
      val accountName = msg.accountName

      // Get or create account actor
      val account = context.children.find(_.path.name == accountName) match {
        case Some(ref) => {
          // Already loaded
          ref
        }
        case None => {
          // Load from storage
          val user = registry.storageService.loadAccount(accountName)
          if (user == null) throw new IllegalStateException("User account not yet created")

          // Create child
          // TODO: Load account from storage, along with its characters
          context.actorOf(Props(new Account(accountName)), accountName)
        }
      }

      // Tell the account which session controls it
      account ! ControlSessionConnected(msg.controllingSession)
    }

  }
}

case class SetControllingSession(accountName: String, controllingSession: Long)


