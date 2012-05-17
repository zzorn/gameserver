package org.skycastle.server.services.control

/**
 * A listener that is notified of all perceived changes around and in an agent.
 */
trait PerceptionListener {

  def onPerception(perception: Perception)

}
