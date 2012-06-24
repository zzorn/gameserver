package org.skycastle.server.actors

import akka.actor.{Props, Actor}
import org.skycastle.server.registry.Registry


/**
 * Root actor for the game.
 */
class GameRootActor(registry: Registry) extends Actor {

  override def preStart() {
    // Load from previously saved state, if found
    val found = loadGame()

    // If not found, create a new world
    if (!found) createGame()
  }

  protected def receive = Actor.emptyBehavior


  private def loadGame(): Boolean = {
    // TODO: Load
    false
  }

  private def createGame() {
    context.actorOf(Props(new World()), "world")
    context.actorOf(Props(new AccountManager(registry)), "accounts")
  }


}