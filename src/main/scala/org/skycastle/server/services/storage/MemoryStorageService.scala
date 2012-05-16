package org.skycastle.server.services.storage

import org.skycastle.server.models.{Ref, Model}
import java.util.HashMap
import org.skycastle.server.utils.ParameterChecker

/**
 * Just stores things in memory.  Useful for testing.
 */
class MemoryStorageService extends StorageService {

  private val storage = new HashMap[Long, Model]()

  private var _nextFreeId: Long = 1


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
    _nextFreeId += 1
    _nextFreeId - 1
  }
}