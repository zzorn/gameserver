package org.skycastle.server.registry

import org.skycastle.server.services.storage.StorageService

/**
 *
 */
trait Registry {

  def storageService: StorageService

}