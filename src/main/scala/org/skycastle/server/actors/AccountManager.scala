package org.skycastle.server.actors

import akka.actor.{ActorRef, Props, Actor}
import org.skycastle.server.registry.Registry
import org.skycastle.server.services.authenticator.{AccountCreationError, AccountCreated, AccountCreationResponse}

/**
 *
 */
class AccountManager(registry: Registry) extends Actor {

  protected def receive = {
    case msg: CreateAccount => {

      // TODO: Combine User and Account classes
      registry.authenticationService.createAccount(msg.accountName, msg.pw) match {
        case AccountCreated => {
          val account = context.actorOf(Props(new Account(msg.accountName)), msg.accountName)
          sender ! AccountCreatedOk(account)
        }
        case error: AccountCreationError => {
          sender ! ErrorMessage("CreateAccount", error.errorCode.name)
        }
      }
    }
  }
}

case class CreateAccount(accountName: String, pw: Array[Char])

case class AccountCreatedOk(account: ActorRef)