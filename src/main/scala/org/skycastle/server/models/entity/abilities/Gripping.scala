package org.skycastle.server.models.entity.abilities

import org.skycastle.server.models.{EntityId, Ref}
import org.skycastle.server.models.entity.{Action, Entity}
import org.skycastle.server.utils.Vec3
import org.skycastle.server.registry.Registry

/**
 * Indicates that the entity can grip and move other entities, as well as squeeze and manipulate them in various ways.
 */
// TODO: Should we combine gripping and manipulation capabilities?  What about having several abilities that differ?
// TODO: We need some generic trait for things that may contain entities
class Gripping {

  var grippers: Map[Symbol, Gripper] = Map()

  @Action
  def grabFromContainer(registry: Registry, actor: EntityId, entityId: EntityId, gripper: Symbol, sourceContainer: EntityId) {
    // TODO: Ensure container is in inventory (our own container) or on a slot, or in a container contained in the inventory or a slot

    // TODO: Remove entity from old location

    // Put entity in the specified gripper, if it is free and can handle the entity
    val entity = registry.storageService.getEntity(entityId)
    putInGripper(gripper, entity)
  }

  @Action
  def grabFromSlot(registry: Registry, actor: EntityId, entity: EntityId, gripper: Symbol, sourceSlot: Symbol) {

  }

  @Action
  def grabFromGround(registry: Registry, actor: EntityId, entity: EntityId, gripper: Symbol) {
  }


  @Action
  def putInContainer(registry: Registry, actor: EntityId, entity: EntityId, gripper: Symbol, targetContainer: EntityId) {
  }

  @Action
  def putInSlot(registry: Registry, actor: EntityId, entity: EntityId, gripper: Symbol, targetSlot: Symbol) {
  }

  @Action
  def putOnGround(registry: Registry, actor: EntityId, entity: EntityId, gripper: Symbol, targetPos: Vec3, targetDirection: Vec3) {
  }


  @Action
  def throwItem(registry: Registry, actor: EntityId, entity: EntityId, gripper: Symbol, direction: Vec3, force_N: Double) {
  }

  private def putInGripper(gripper: Symbol, entity: Entity) {

  }

  private def removeFromGripper(gripper: Symbol, entity: EntityId) {

  }



}


class Gripper {

  var reach_m = 1.0
  var gripForce_N = 1.0
  var moveForce_N = 10.0
  var maxSize_m = 1.0
  var minSize_m = 0.01
  var precision_m = 0.01

  var heldItem: EntityId = null

  def canHold(item: Entity): Boolean = {
    // TODO
    true
  }

}