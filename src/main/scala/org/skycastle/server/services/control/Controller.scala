package org.skycastle.server.services.control

import org.skycastle.server.models.entity.Entity
import org.skycastle.server.registry.Registry
import java.util.HashMap
import java.lang.reflect.Method
import org.skycastle.server.utils.ParameterChecker

/**
 * Handles control of some entity
 */
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


  def findActionMethods() {
    actionMethods = controlledEntity.getAbilities.map(_.getActionMethods).foldLeft(Map[Symbol, Method]()) {_ ++ _}
  }

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
