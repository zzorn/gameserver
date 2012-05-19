package org.skycastle.server.services.authenticator

import org.skycastle.server.registry.Registry
import org.skycastle.server.models.account.User
import org.mindrot.jbcrypt.BCrypt
import org.skycastle.server.utils.StringUtils

/**
 *
 */
final class AuthenticationServiceImpl(context: Registry) extends AuthenticationService {

  def createAccount(accountName: String, pw: Array[Char]): AccountCreationResponse = {
    val result = if (!isValidAccountName(accountName)) AccountNameUnavailable
    else if (context.storageService.accountExists(accountName)) AccountNameUnavailable
    else if (tooWeakPassword(pw, accountName)) TooWeakPassword
    else {

      // Create hashed password
      // NOTE: Storing a password in a string has the drawback of leaving it in plaintext in memory until the string is
      // garbage collected and the memory is reused.  An Array[Char] can be wiped when the password is no longer used.
      // However, BCrypt wants the password in a String parameter, so pass it for now.
      val hashedPassword = BCrypt.hashpw(new String(pw), BCrypt.gensalt(12));
      clearCharArray(pw)

      val account = new User(accountName)
      account.hashedPassword = hashedPassword

      val success = context.storageService.saveNewAccount(accountName, account)
      if (success) {
        // Do other account initialization
        context.accountService.initializeNewAccount(account)

        // Save updates
        context.storageService.updateAccount(accountName, account)

        AccountCreated
      }
      else AccountNameUnavailable // Someone else grabbed the name while we were creating the account
    }

    clearCharArray(pw)
    result
  }


  def authenticate(accountName: String, pw: Array[Char]): Option[User] = {
    val result = if (!isValidAccountName(accountName)) None
    else {
      val account = context.storageService.loadAccount(accountName)
      if (account == null) None
      else {
        if (BCrypt.checkpw(new String(pw), account.hashedPassword)) {
          Some(account)
        }
        else {
          None
        }
      }
    }
    clearCharArray(pw)
    result
  }


  def init() {}

  def shutdown() {}

  private def isValidAccountName(accountName: String): Boolean = {
    accountName != null &&
      accountName.length >= 3 &&
      StringUtils.isIdentifier(accountName)
  }

  private def tooWeakPassword(pw: Array[Char], accountName: String): Boolean = {
    pw == null || pw.length < 8 || accountName.toArray == pw
  }

  private def clearCharArray(array: Array[Char]) {
    var i = 0
    while (i < array.length) {
      array(i) = ' '
      i += 1
    }
  }


}



