package org.skycastle.server.registry

import org.skycastle.server.services.storage.MemoryStorageService
import org.skycastle.server.services.network.MinaNetworkService
import org.skycastle.server.services.Service
import org.skycastle.server.services.authenticator.AuthenticationServiceImpl
import org.skycastle.server.services.account.AccountServiceImpl
import org.skycastle.server.utils.Logging

/**
 *
 */
class RegistryImpl extends Registry with Logging {

  private var services: List[Service] = Nil

  val port = 6283

  val storageService = addService(new MemoryStorageService(this))
  val accountService = addService(new AccountServiceImpl(this))
  val authenticationService = addService(new AuthenticationServiceImpl(this))
  val networkService = addService(new MinaNetworkService(this, port, true))

  def init() {
    services foreach {service =>
      log.info("Initializing service " + service.getClass.getSimpleName)
      service.init()
    }
  }

  def shutdown() {
    services foreach {service =>
      log.info("Shutting down service " + service.getClass.getSimpleName)
      service.shutdown()
    }
  }

  private def addService[T <: Service](service: T): T = {
    services = services ::: List(service)
    service
  }
}