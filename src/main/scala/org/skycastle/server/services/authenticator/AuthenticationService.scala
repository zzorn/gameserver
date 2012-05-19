package org.skycastle.server.services.authenticator
import org.skycastle.server.models.Ref
import org.skycastle.server.models.account.Account
import org.skycastle.server.services.Service


sealed trait AccountCreationResponse
case object AccountCreated extends AccountCreationResponse

object AccountCreationError { def unapply(ace: AccountCreationError) = Some(ace.errorCode) }
abstract class AccountCreationError(val errorCode: Symbol) extends AccountCreationResponse
case object AccountNameUnavailable extends AccountCreationError('AccountNameUnavailable)
case object TooWeakPassword extends AccountCreationError('TooWeakPassword)
case object AccountCreationDenied extends AccountCreationError('AccountCreationDenied)




/**
 * Takes care of authenticating a login and creating new accounts.
 */
trait AuthenticationService extends Service {

  def authenticate(accountName: String, pw: Array[Char]): Option[Account]

  def createAccount(accountName: String, pw: Array[Char]): AccountCreationResponse

}

