package org.skycastle.server.models.account

import org.skycastle.server.models.{EntityId, Model}
import java.util.ArrayList


/**
 *
 */
class User(val accountName: String) {

  var hashedPassword: String = null

  var currentCharacter: EntityId = null

  var characters: ArrayList[EntityId] = new ArrayList[EntityId]()

}