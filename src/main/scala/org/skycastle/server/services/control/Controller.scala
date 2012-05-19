package org.skycastle.server.services.control

import org.skycastle.server.models.entity.Entity
import org.skycastle.server.registry.Registry
import java.util.HashMap
import java.lang.reflect.Method
import org.skycastle.server.utils.ParameterChecker

/**
 * Handles control of some entity
 */
// TODO: Should this be a controllable ability?
class Controller(registry: Registry) {

  private var listeners: List[PerceptionListener] = Nil

  private var actionMethods: Map[Symbol, Method] = Map()

  private var _controlledEntity: Entity = null

  def controlledEntity = _controlledEntity

  def controlledEntity_=(entity: Entity) {
    ParameterChecker.requireHasId(entity, 'entity)
    _controlledEntity = entity
    findActionMethods()
  }

  /**
   * Called when the controlled entity changes
   */
  def findActionMethods() {
    actionMethods = controlledEntity.getAbilities.values.map(_.getActionMethods).foldLeft(Map[Symbol, Method]()) {_ ++ _}
  }

  /**
   * Called by the entity or sensors related to it, when it perceives something.
   * Sends the perception to the player / ai controlling the entity.
   */
  def sendPerception(perception: Perception) {
    // TODO: Accumulate packets, send when enough or enough time since the first?
    // To start with, just send them immediately
    listeners foreach {_.onPerception(perception)}
  }

  /**
   * Invoked to do an action with the controlled entity
   * @param action id of action to do
   * @param parameters named parameters for action
   */
  def doAction(action: Symbol, parameters: Map[Symbol, Any]) {
    actionMethods.get(action) match {
      case None => throw new Error("No action named "+action.name+" found for entity " + controlledEntity)
      case Some(method: Method) =>
        // TODO: Match parameters to method parameters, check types
        // TODO: If method has an entity parameter named host, or a Registry parameter, provide them
        // TODO: Maybe convert EntityId:s to Entities by fetching them from the storage?
    }
  }

  def addListener(listener: PerceptionListener) {
    listeners ::= listener
  }

  def removeListener(listener: PerceptionListener) {
    listeners = listeners.filterNot(_ == listener)
  }

}
