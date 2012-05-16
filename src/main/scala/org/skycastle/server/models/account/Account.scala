package org.skycastle.server.models.account

import org.skycastle.server.models.{Ref, Model}
import org.skycastle.server.models.agent.Agent


/**
 *
 */
class Account extends Model {

  var characters: List[ModelRef[Agent]] = Nil

}