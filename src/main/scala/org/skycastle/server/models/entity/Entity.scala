package org.skycastle.server.models.entity

import abilities._
import org.skycastle.server.utils.ParameterChecker
import org.skycastle.server.models.{UndefinedId, EntityId, Model}

import scala.collection.JavaConversions._
import scala.transient
import org.skycastle.server.services.network.protocol.Message
import org.skycastle.server.services.network.{GameSession}
import org.skycastle.server.registry.Registry

/**
 *
 */
// TODO: Listener that is notified when abilities are added or removed or changed?
class Entity extends Model {

  // Different abilities that the entity may have, or null if entity doesn't have that ability.
  // When storing the entity, ideally just store the abilities with non-null values, and ignore the rest, to minimize storage size.

  private var abilities: Map[Symbol, Ability] = Map()
  @transient private var listeners: List[EntityListener] = null
  @transient private lazy val _entityId = EntityId(id)

  private var controllingSession: Long = 0L

  /**
   * @return the entityId of this entity, or UndefinedId if it has not yet been stored.
   */
  def entityId: EntityId = {
    if (id == 0) UndefinedId
    else _entityId
  }

  def setAbility(abilityName: Symbol, ability: Ability) {
    ParameterChecker.requireIsIdentifier(abilityName, 'abilityName)

    abilities += abilityName -> ability
  }

  def hasAbility(abilityName: Symbol): Boolean = abilities.contains(abilityName)

  def getAbility[T <: Ability](abilityName: Symbol): T = {
    ParameterChecker.requireIsIdentifier(abilityName, 'abilityName)
    if (!abilities.contains(abilityName)) throw new Error("The ability '"+abilityName.name+"' is not available for entity "+this)

    abilities(abilityName).asInstanceOf[T]
  }

  /**
   * @return the abilities available for this entity, or an empty map if the entity has no defined abilities.
   */
  def getAbilities: Map[Symbol, Ability] = if (abilities == null) Map() else abilities


  def addListener(entityListener: EntityListener) {
    if (listeners == null) listeners = List(entityListener)
    else listeners ::= entityListener
  }

  def removeListener(entityListener: EntityListener) {
    if (listeners != null) listeners -= entityListener
  }


  def handleMessage(registry: Registry, message: Message): Boolean = {
    // Handle with first matching handler, return true if message handled
    val handled = (abilities.values find( _.handleMessage(registry, message))) != null

    // TODO: Some mechanism for abilities to indicate whether the entity should be saved?
    if (handled) registry.storageService.saveEntity(this)
    handled
  }

  def onControllingSessionChanged(registry: Registry, session: GameSession) {
    if (session == null) controllingSession = 0L
    else controllingSession = session.getId

    abilities.values foreach( _.onControllingSessionChanged(registry, session))

    registry.storageService.saveEntity(this)
  }
}
