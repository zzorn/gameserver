package org.skycastle.server.services.control

import org.skycastle.server.models.entity.Entity

/**
 * Handles control of some entity
 */
class Controller {

  private var listeners: List[PerceptionListener] = Nil

  var controlledEntity: Entity = null

  def doAction(action: Symbol, parameters: AnyRef) {
    // TODO
  }

  def addListener(listener: PerceptionListener) {
    listeners ::= listener
  }

  def removeListener(listener: PerceptionListener) {
    listeners = listeners.filterNot(_ == listener)
  }

}
