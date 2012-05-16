package org.skycastle.server.models.entity.abilities

import java.util.{List, Collections, ArrayList}
import org.skycastle.server.utils.ParameterChecker
import org.skycastle.server.models.EntityId
import org.skycastle.server.models.entity.{Ability, Entity}

/**
 * Entity can contain other things.
 */
class Containing extends Ability{

  var capacity_kg = 10.0
  var capacityVolume = 10.0

  /**
   * Maximum number of individual items that can be stored in the container.
   */
  var capacityCount = 10

  /**
   * True if container is completely closable, false if contents may spill out if container is turned upside down.
   */
  var hasLid = true

  /**
   * Smallest particle size that the container can hold
   */
  var minParticleSize_m = 0.01


  private final val _contents: ArrayList[EntityId] = new ArrayList[EntityId]()
  private final val unmodifiableContents: List[EntityId] = Collections.unmodifiableList(_contents)

  /**
   * Contents of the container.
   */
  def contents: java.util.List[EntityId] = unmodifiableContents

  def canAdd(item: Entity): Boolean = {
    ParameterChecker.requireNotNull(item, 'item)
    // TODO: Check total weight, volume
    _contents.size() < capacityCount
  }

  def add(item: Entity) {
    ParameterChecker.requireHasId(item, 'item)
    if (canAdd(item) && !_contents.contains(item)) {
      _contents.add(item.entityId)
    }
  }

  def remove(itemId: EntityId) {
    if (_contents.contains(itemId)) {
      _contents.remove(itemId)
    }
  }

}
