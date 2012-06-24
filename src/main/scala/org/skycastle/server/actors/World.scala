package org.skycastle.server.actors

import akka.actor.{Props, ActorRef, Actor}
import org.skycastle.server.utils.Vec3

/**
 * Represents a game world.
 */
class World(planetSize: PlanetSize = PlanetSize(),
            spawnPos: Vec3 = Vec3(),
            spawnDir: Vec3 = Vec3(1,0,0)) extends Actor {

  private var planet: ActorRef = null
  private var defaultSpawner: ActorRef = null


  override def preStart() {
    // Create the planet
    val startPlanet = "planet"
    planet = context.actorOf(Props(new Planet(planetSize)), startPlanet)

    // Setup start pos
    defaultSpawner = context.actorOf(Props[Spawner], "defaultSpawnPos")
    val startBlock = context.actorFor(startPlanet + "/" + planetSize.blockName(0, 0))
    val startLocation = Location(startBlock, spawnPos, spawnDir)
    defaultSpawner ! SetLocation(startLocation)
  }

  protected def receive = {

    case msg: EnterAvatar => {
      defaultSpawner ! SpawnEntity(msg.avatar)
    }

  }

}

case class EnterAvatar(avatar: ActorRef)
