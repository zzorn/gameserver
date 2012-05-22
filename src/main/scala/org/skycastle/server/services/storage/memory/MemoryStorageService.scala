package org.skycastle.server.services.storage.memory

import org.skycastle.server.models.{Ref, Model}
import org.skycastle.server.utils.ParameterChecker
import org.skycastle.server.registry.Registry
import java.util.concurrent.ConcurrentHashMap
import org.skycastle.server.models.account.User
import java.util.concurrent.atomic.AtomicLong
import org.skycastle.server.services.storage.StorageService

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
  def getOrNull[T <: Model](id: Long)(implicit kind: Class[T]): T = {
    ParameterChecker.requireNotNull(id, 'id)
    val value: Model = storage.get(id)
    if (!kind.isInstance(value)) throw new Error("Can not get entry with id "+id+" with a value of '" + value + "' as type " + kind)
    value.asInstanceOf[T]
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
    ParameterChecker.requireIsIdentifier(accountName, 'accountName)

    accounts.get(accountName)
  }

  def accountExists(accountName: String): Boolean = {
    ParameterChecker.requireIsIdentifier(accountName, 'accountName)

    accounts.contains(accountName)
  }

  def init() {
    storage.clear()
  }

  def shutdown() {}
}