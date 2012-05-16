package org.skycastle.server.models.entity.abilities

import org.skycastle.server.models.agent.Agent
import org.skycastle.server.models.Ref

/**
 * An entity that is owned by some agent.
 */
class Owned {

  var owner: Ref[Agent] = null

}
