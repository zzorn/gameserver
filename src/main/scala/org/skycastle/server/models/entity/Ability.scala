package org.skycastle.server.models.entity

import scala.collection.JavaConversions._
import org.skycastle.server.services.network.protocol.Message
import org.skycastle.server.services.network.GameSession
import org.skycastle.server.registry.Registry

/**
 *
 */
trait Ability {

  /**
   * @param message a message from the controller of the entity (a player, or AI).
   * @return true if handled
   */
  def handleMessage(registry: Registry, message: Message): Boolean = {false}

  /**
   * Called when a (new) session takes control over this entity.  Called with null as parameter when no session no
   * longer controls it.
   * Abilities might for example want to notify the session about their current state.
   */
  def onControllingSessionChanged(registry: Registry, session: GameSession) {}


    /*
      def getActionMethods: Map[Symbol, Method] = {
        (getClass.getMethods.toList flatMap {method =>
          val actionAnnotation: Action = method.getAnnotation(classOf[Action])
          if (actionAnnotation != null) {
            val value = actionAnnotation.value()
            val name = if (StringUtils.isIdentifier(value)) Symbol(value) else Symbol(method.getName)
            Some((name, method))
          }
          else {
            None
          }
        }).toMap
      }
    */
}