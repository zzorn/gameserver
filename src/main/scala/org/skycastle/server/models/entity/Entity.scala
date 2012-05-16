package org.skycastle.server.models.entity

import abilities._
import java.lang.reflect.Field
import org.skycastle.server.utils.ParameterChecker


/**
 *
 */

class Entity {

  // Different abilities that the entity may have, or null if entity doesn't have that ability.
  // When storing the entity, ideally just store the abilities with non-null values, and ignore the rest, to minimize storage size.

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



  def hasAbility(abilityName: Symbol): Boolean = getAbility(abilityName) != null

  def getAbility[T <: AnyRef](abilityName: Symbol): T = {
    ParameterChecker.requireIsIdentifier(abilityName, 'abilityName)

    val field: Field = getClass.getField(abilityName.name)
    if (field == null) throw new Error("No ability with the name "+abilityName.name +" can exist for an entity!")
    else field.get().asInstanceOf[T]
  }


}
