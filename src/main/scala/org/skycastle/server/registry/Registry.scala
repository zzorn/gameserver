package org.skycastle.server.registry

import org.skycastle.server.services.storage.StorageService
import org.skycastle.server.services.network.NetworkService
import org.skycastle.server.services.account.AccountService
import org.skycastle.server.services.authenticator.AuthenticationService
import org.skycastle.server.services.actor.ActorService

/**
 *
 */
trait Registry {

  def authenticationService: AuthenticationService
  def accountService: AccountService
  def storageService: StorageService
  def networkService: NetworkService
  def actorService: ActorService

  /**
   * Start all services, and the server.
   */
  def init()

  /**
   * Shutdown all services, and the server.
   */
  def shutdown()
}