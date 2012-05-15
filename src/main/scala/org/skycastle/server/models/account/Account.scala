package org.skycastle.server.models.account

import org.skycastle.server.models.{ModelRef, Model}
import org.skycastle.server.models.agent.Agent


/**
 *
 */
class Account extends Model {

  var characters: List[ModelRef[Agent]] = Nil

}