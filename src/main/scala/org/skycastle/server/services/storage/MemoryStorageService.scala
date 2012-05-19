package org.skycastle.server.services.storage

import org.skycastle.server.models.{Ref, Model}
import org.skycastle.server.utils.ParameterChecker
import org.skycastle.server.registry.Registry
import java.util.concurrent.ConcurrentHashMap
import org.skycastle.server.models.account.User
import java.util.concurrent.atomic.AtomicLong

/**
 * Just stores things in memory.  Useful for testing.
 */
class MemoryStorageService(registry: Registry) extends StorageService {

  private val storage = new ConcurrentHashMap[Long, Model]()
  private val accounts = new ConcurrentHashMap[String, User]()

  private val _nextFreeId: AtomicLong = new AtomicLong(1)


  /**
   * @return the model with the specified id.  Throws an exception if not found.
   */
  def getOrNull[T <: Model](id: Long): T = {
    ParameterChecker.requireNotNull(id, 'id)
    storage.get(id).asInstanceOf[T]
  }

  /**
   * Stores the specified model to the storage.
   * @return the id of the stored model.
   *         If the stored model did not yet have any id, a new one is generated and returned, as well as assigned to it.
   */
  def save[T <: Model](model: T): Long = {
    ParameterChecker.requireNotNull(model, 'model)

    if (model.id == 0) model.setId(nextFreeId)
    storage.put(model.id, model)
    model.id
  }

  private def nextFreeId: Long = {
    _nextFreeId.getAndIncrement
  }


  def saveNewAccount(accountName: String, account: User): Boolean = {
    ParameterChecker.requireIsIdentifier(accountName, 'accountName)

    val existing = accounts.putIfAbsent(accountName, account)

    // Return true on success (no earlier value)
    existing == null
  }

  def updateAccount(accountName: String, account: User) {
    ParameterChecker.requireIsIdentifier(accountName, 'accountName)

    accounts.put(accountName, account)
  }

  def loadAccount(accountName: String): User = {
    accounts.get(accountName)
  }

  def accountExists(accountName: String): Boolean = {
    accounts.contains(accountName)
  }

  def init() {storage.clear()}
  def shutdown() {}
}