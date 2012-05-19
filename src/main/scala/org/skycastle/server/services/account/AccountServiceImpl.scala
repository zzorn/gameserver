package org.skycastle.server.services.account

import org.skycastle.server.registry.Registry
import org.skycastle.server.models.entity.Entity
import org.skycastle.server.models.account.Account
import org.skycastle.server.models.{ModelRef, Ref}

/**
 *
 */
class AccountServiceImpl(context: Registry) extends AccountService {


  def initializeNewAccount(account: Account) {
    // Do any needed account initialization here, other than name and password
  }

  def createNewCharacter(account: Account): Ref[Entity] = {
    // TODO
    null
  }

  def init() {}

  def shutdown() {}

}