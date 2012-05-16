package org.skycastle.server.models.entity.abilities

import org.skycastle.server.models.entity.Entity
import org.skycastle.server.utils.ParameterChecker

/**
 * An entity that can contain other entities (with some required abilities) in various named slots.
 * E.g. clothing slots, bag slots, wielded tools, etc.
 * Could also be used for e.g. donkey that can carry a saddle bag, an engineer vest with pockets,
 * a hook on a wall that can hold a hat and something hangable like a cloak, etc.
 * Maybe even a bench with seats, a car with driver, gunner, navigator positions, and so on.
 */
class Slotted {

  var slots: List[Slot] = Nil


}

class Slot(id: Symbol, requiredAbilities: List[Symbol]) {
  private var _content: Entity = null

  def content: Entity = _content
  def content_=(item: Entity) {
    if (item != null && !canHold(item)) throw new Error("The item slot "+id.name+" can not hold item '"+item+"', as it does not have all of the the required abilities "+requiredAbilities.map(_.name).mkString(", "))
    _content = item
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
