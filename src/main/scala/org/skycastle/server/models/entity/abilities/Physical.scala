package org.skycastle.server.models.entity.abilities

import org.skycastle.server.utils.Vec3
import org.skycastle.server.models.entity.Ability


/**
 * Indicates that the entity has some physical mass, movement, and volume.
 */
class Physical extends Ability {

  var mass_kg = 1.0
  var volume_m3 = 1.0

  var onSurface = true
  // TODO: Some reference to the surface - a room wall/floor, or a terrain, or some entity


  val pos: Vec3 = Vec3()
  val velocity: Vec3 = Vec3()
  val acceleration: Vec3 = Vec3()

  // TODO: Rotation quaternions for direction, rotation, and torque

  // TODO: Air resistance values, ground friction values, ground roll values? (how easily the item rolls around each axis)

  // TODO: Bounding volume

  def update(seconds: Double) {
    velocity +*= (acceleration, seconds)
    pos      +*= (velocity, seconds)

    // TODO: Air resistance, etc.
    // TODO: Friction
    // TODO: Obstacles?
    // TODO: Following ground / walls

    // TODO: For things with a max speed, there is no need to do step by step updates
  }

}
