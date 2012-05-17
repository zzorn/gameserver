package org.skycastle.server.models.entity

import abilities._
import java.lang.reflect.Field
import org.skycastle.server.utils.ParameterChecker
import org.skycastle.server.models.{UndefinedId, EntityId, Model}

import scala.collection.JavaConversions._
/**
 *
 */
// TODO: Listener that is notified when abilities are added or removed or changed?
class Entity extends Model {



  // Different abilities that the entity may have, or null if entity doesn't have that ability.
  // When storing the entity, ideally just store the abilities with non-null values, and ignore the rest, to minimize storage size.

  private @transient var listeners: List[EntityListener] = null
  private var abilities: Map[Symbol, Ability] = Map()

  def setAbility(abilityName: Symbol, ability: Ability) {
    abilities += abilityName -> ability
  }

  var physical: Physical = null
  var containing: Containing = null
  var owned: Owned = null
  var materialized: Materialized = null
  var located: Located = null
  var wearable: Wearable = null
  var slotted: Slotted = null

  // TODO: Controllable?
  // TODO: Appearance
  // TODO: Spacey?

  private lazy val _entityId = EntityId(id)

  /**
   * @return the entityId of this entity, or UndefinedId if it has not yet been stored.
   */
  def entityId: EntityId = {
    if (id == 0) UndefinedId
    else _entityId
  }

  def hasAbility(abilityName: Symbol): Boolean = getAbility(abilityName) != null

  def getAbility[T <: AnyRef](abilityName: Symbol): T = {
    ParameterChecker.requireIsIdentifier(abilityName, 'abilityName)

    val field: Field = getClass.getField(abilityName.name)
    if (field == null) throw new Error("No ability with the name "+abilityName.name +" can exist for an entity!")
    else {
      val ability: T = field.get(this).asInstanceOf[T]
      if (ability == null) throw new Error("The ability '"+abilityName.name+"' is not currently available for entity "+this)
      else ability
    }
  }

  /**
   * @return the abilities available for this entity, or an empty list if the entity has no defined abilities.
   */
  def getAbilities: List[Ability] = {
    var abilities: List[Ability] = Nil
    getClass.getFields foreach {field =>
      if (classOf[Ability].isAssignableFrom(field.getType)) {
        val ability: Ability = field.get(this).asInstanceOf[Ability]
        if (ability != null) abilities ::= ability
      }
    }
    abilities
  }

  def addListener(entityListener: EntityListener) {
    if (listeners == null) listeners = List(entityListener)
    else listeners ::= entityListener
  }

  def removeListener(entityListener: EntityListener) {
    if (listeners != null) listeners -= entityListener
  }
}
