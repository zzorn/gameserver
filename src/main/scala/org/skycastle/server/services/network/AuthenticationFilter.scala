package org.skycastle.server.services.network

import org.skycastle.server.registry.Registry
import org.skycastle.server.utils.Logging
import org.apache.mina.core.session.IoSession
import org.apache.mina.core.filterchain.{IoFilter, IoFilterAdapter}
import org.skycastle.server.services.authenticator.{AccountCreationError, AccountCreated}

/**
 * Filter that requires users to either authenticate or create a new account before it forwards their messages.
 */
class AuthenticationFilter(context: Registry) extends IoFilterAdapter with Logging {

  private def createResponse(responseType: Symbol, desc: Symbol): Message = {
    Message(responseType, Map('desc -> desc))
  }

  private def createError(errorType: Symbol): Message = createResponse('error, errorType)

  private val notAuthenticatedError = createError('NotAuthenticated)
  private val missingAccount = createError('MissingAccountName)
  private val missingPassword = createError('MissingPassword)
  private val invalidLogin = createError('InvalidAccountNameOrPassword)
  private val unknownMessage = createError('UnknownMessage)
  private val loginOk = createResponse('loginResponse, 'LoginOk)
  private val createdOk = createResponse('accountCreationResponse, 'AccountCreated)

  override def messageReceived(nextFilter: IoFilter.NextFilter, session: IoSession, receivedObject: Any) {

    def onError(error: Message) = {
      log.warn("Session " + session + ": " + error.parameters.getOrElse('desc, 'UnknownError))
      session.write(error)

      // Close a session if it does something wrong
      session.close(false)
    }

    def getAccountAndPw(data: Message): Option[(String, Array[Char])] = {
      val accountName = data.parameters.getOrElse('account, "").asInstanceOf[String]
      val pw = data.parameters.getOrElse('pw, "").asInstanceOf[String].toCharArray
      if (accountName == null || accountName.length == 0) {
        onError(missingAccount)
        None
      }
      else if (pw == null && pw.length == 0) {
        onError(missingPassword)
        None
      }
      else Some((accountName, pw))
    }

    def login(data: Message) {
      // Authenticate
      getAccountAndPw(data) match {
        case Some((accountName, pw)) => context.authenticationService.authenticate(accountName, pw) match {
          case Some(account) =>
            session.setAttribute("ACCOUNT", account.name)
            log.info("User " + account.name + " logged in.")
            session.write(loginOk)
          case None => onError(invalidLogin)
        }
        case _ => // Error messages already sent.
      }
    }

    def createAccount(data: Message) {
      // Create a new account
      getAccountAndPw(data) match {
        case Some((accountName, pw)) => context.authenticationService.createAccount(accountName, pw) match {
          case AccountCreated =>
            session.setAttribute("ACCOUNT", accountName)
            log.info("Created a new account for user " + accountName)
            session.write(createdOk)
          case AccountCreationError(errorCode) =>
            onError(createResponse('accountCreationResponse, errorCode))
        }
        case _ => // Error messages already sent.
      }
    }

    receivedObject match {
      case message: Message =>
        // DEBUG:
        println("Auth filter Received message = " + message)

        val accountName = session.getAttribute("ACCOUNT").asInstanceOf[String]
        if (accountName != null) {
          // Logged in already, forward message
          nextFilter.messageReceived(session, message)
        }
        else {
          // Not logged in, allow only login or create account actions:
          if (message.action == 'login) login(message)
          else if (message.action == 'createAccount) createAccount(message)
          else onError(notAuthenticatedError) // Not logged in and didn't get login message
        }
      case _ => onError(unknownMessage) // Received object wasn't a Message
    }
  }
}
