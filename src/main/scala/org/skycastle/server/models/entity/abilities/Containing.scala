package org.skycastle.server.models.entity.abilities

import org.skycastle.server.models.entity.Entity
import java.util.{List, Collections, ArrayList}
import org.skycastle.server.utils.ParameterChecker

/**
 * Entity can contain other things.
 */
class Containing {

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


  private final val _contents: ArrayList[Entity] = new ArrayList[Entity]()
  private final val unmodifiableContents: List[Entity] = Collections.unmodifiableList(_contents)

  /**
   * Contents of the container.
   */
  def contents: java.util.List[Entity] = unmodifiableContents

  def canAdd(host: Entity, item: Entity): Boolean = {
    ParameterChecker.requireNotNull(item, 'item)
    // TODO: Check total weight, volume
    _contents.size() < capacityCount
  }

  def add(host: Entity, item: Entity) {
    ParameterChecker.requireNotNull(item, 'item)
    if (canAdd(host, item) && !_contents.contains(item)) {
      _contents.add(item)
    }
  }

  def remove(host: Entity, item: Entity) {
    if (_contents.contains(item)) {
      _contents.remove(item)
    }
  }

}
