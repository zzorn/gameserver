package org.skycastle.server.services.account

import org.skycastle.server.services.Service
import org.skycastle.server.models.Ref
import org.skycastle.server.models.account.User
import org.skycastle.server.models.entity.Entity

/**
 *
 */
trait AccountService  extends Service {

  def initializeNewAccount(account: User)

  def createNewCharacter(account: User): Ref[Entity]

}