package org.skycastle.server.models.entity.abilities

import org.skycastle.server.registry.Registry
import org.skycastle.server.models.entity.{Action, Entity, Ability}


/**
 *
 */
// TODO: Maybe move to environment?
class Moving extends Ability {

  @Action
  def moveOnGround(registry: Registry, entity: Entity, direction: Double, speed: Double) {
    // TODO: Wrap parameter to bounds
    // TODO: Update movement
    // TODO: Calculate any affecting forces
    // TODO: Calculate next time we need to update / check movement
    // TODO: Store updated entity
  }

}