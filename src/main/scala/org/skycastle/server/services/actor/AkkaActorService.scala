package org.skycastle.server.services.actor

import akka.actor.{Props, ActorSystem}
import org.skycastle.server.actors.GameRootActor
import org.skycastle.server.registry.Registry

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

}