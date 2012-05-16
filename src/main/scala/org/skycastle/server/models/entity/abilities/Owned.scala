package org.skycastle.server.models.entity.abilities

import org.skycastle.server.models.agent.Agent
import org.skycastle.server.models.{EntityId, Ref}
import org.skycastle.server.models.entity.Ability

/**
 * An entity that is owned by some agent.
 */
class Owned extends Ability {

  var owner: EntityId = null

}
