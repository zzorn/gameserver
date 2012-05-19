package org.skycastle.server.models.account

import org.skycastle.server.models.{EntityId, Model}


/**
 *
 */
class Account(val name: String) {

  var hashedPassword: String = null

  var characters: List[EntityId] = Nil

}