package org.skycastle.server.models.entity.abilities

import org.skycastle.server.utils.ParameterChecker
import org.skycastle.server.models.EntityId
import org.skycastle.server.models.entity.{Ability, Entity}

/**
 * An entity that can contain other entities (with some required abilities) in various named slots.
 * E.g. clothing slots, bag slots, wielded tools, etc.
 * Could also be used for e.g. donkey that can carry a saddle bag, an engineer vest with pockets,
 * a hook on a wall that can hold a hat and something hangable like a cloak, etc.
 * Maybe even a bench with seats, a car with driver, gunner, navigator positions, and so on.
 */
// TODO: Clothing can be stacked on a slot, as long as size allows.  Decreases mobility a bit..
class Slotted extends Ability {

  var slots: List[Slot] = Nil


}

class Slot(val id: Symbol, val slotType: Symbol, requiredAbilities: List[Symbol]) {
  private var _content: EntityId = null

  def content: EntityId = _content
  def content_=(item: Entity) {
    ParameterChecker.requireHasId(item, 'item)
    if (item != null && !canHold(item)) throw new Error("The item slot "+id.name+" can not hold item '"+item+"', as it does not have all of the the required abilities "+requiredAbilities.map(_.name).mkString(", "))
    _content = item.entityId
  }

  /**
   * @return true if the slot can hold the specified item.
   */
  def canHold(item: Entity): Boolean = {
    ParameterChecker.requireNotNull(item, 'item)

    requiredAbilities foreach {requiredAbility =>
      if (!item.hasAbility(requiredAbility)) return false
    }

    true
  }


}
