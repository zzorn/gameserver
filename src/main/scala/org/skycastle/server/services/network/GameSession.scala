package org.skycastle.server.services.network

import org.apache.mina.core.session.IoSession
import org.skycastle.server.registry.Registry
import org.skycastle.server.models.account.Account

/**
 *
 */
class GameSession(registry:  Registry, networkSession: IoSession) {

  private var loggedIn = false
  private var account: Account = null


  def getId: Long = networkSession.getId

  def handleMessage(message: Any) {
    println("Message "+getId + ": "+message)

    if (!loggedIn) {

    }

  }

  def handleSessionClosed() {
    println("Closed "+getId)

  }



}