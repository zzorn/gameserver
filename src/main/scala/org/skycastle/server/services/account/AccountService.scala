package org.skycastle.server.services.account

import org.skycastle.server.services.Service
import org.skycastle.server.models.Ref
import org.skycastle.server.models.account.Account
import org.skycastle.server.models.entity.Entity

/**
 *
 */
trait AccountService  extends Service {

  def initializeNewAccount(account: Account)

  def createNewCharacter(account: Account): Ref[Entity]

}