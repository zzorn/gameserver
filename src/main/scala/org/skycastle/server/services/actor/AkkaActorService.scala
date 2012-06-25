package org.skycastle.server.services.actor

import akka.actor.{ActorRef, Props, ActorSystem}
import org.skycastle.server.actors.{GetAccountData, GameRootActor}
import org.skycastle.server.registry.Registry
import org.skycastle.server.utils.ParameterChecker

/**
 * ActorService implementation that uses the Akka libraries.
 */
class AkkaActorService(registry: Registry) extends ActorService {

  private var system: ActorSystem = null

  def init() {
    system = ActorSystem("ActorSystem")

    // Setup root actor
    system.actorOf(Props(new GameRootActor(registry)), "game")
  }

  def shutdown() {
    system.shutdown()
  }

  def getAccountActor(accountName: String): ActorRef = {
    ParameterChecker.requireIsIdentifier(accountName, 'accountName)

    system.actorFor("/game/accounts") ! GetAccountData(accountName, )

    // Check if we have an actor instantiated for the account
    val accountActor: ActorRef = system.actorFor("/game/accounts/" + accountName)
    accountActor.isTerminated

    // If not, load the account object


    // If not stored, create a new one.

  }
}