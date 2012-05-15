package org.skycastle.server.registry

import org.skycastle.server.services.storage.MemoryStorageService

/**
 *
 */
class TestRegistry extends Registry {

  lazy val storageService = new MemoryStorageService()

}