package org.skycastle.server.actors

import akka.actor.{Props, Actor}
import org.skycastle.server.utils.Vec3

/**
 * A planet, divided up in a number of terrain blocks.
 */
// TODO: Allow division to be adjusted on the fly based on loads, blocks subdividing or merging up as needed (quad tree).
// TODO: Some approximation of a sphere?  Using e.g. icosaeder and triangular blocks?  For now just a torus..
class Planet(val planetSize: PlanetSize) extends Actor {

  override def preStart() {
    // Create the initial terrain blocks making up the planet
    var y = 0
    while (y < planetSize.yBlocks) {
      var x = 0
      while (x < planetSize.xBlocks) {
        context.actorOf(Props(new TerrainBlock(x, y, planetSize)), planetSize.blockName(x, y))

        x += 1
      }
      y += 1
    }
  }

  protected def receive = Actor.emptyBehavior
}

case class PlanetSize(xBlocks: Int = 32,
                      yBlocks: Int = 32,
                      blockSize_m: Double = 10000) {

  def blockName(x: Int, y: Int): String = "block_" + (x % xBlocks) + "_" + (y % yBlocks)
  def blockCount = xBlocks * yBlocks
}

