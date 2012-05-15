package org.skycastle.server.services.storage

import org.skycastle.server.models.{ModelRef, Model}


/**
 *
 */
trait StorageService {

  /**
   * @return the model with the specified id.  Throws an exception if not found.
   */
  def get[T <: Model](id: Long): T = {
    val value: T = getOrNull[T](id)
    if (value == null) throw new Error("No reference found for model with id '"+id+"'")
    else value
  }

  /**
   * @return the model with the specified id.  Throws an exception if not found.
   */
  def getOrNull[T <: Model](id: Long): T

  /**
   * @return the model with the specified reference.  Throws an exception if not found.
   */
  def get[T <: Model](modelRef: ModelRef[T]): T = get(modelRef.id)

  /**
   * @return the model with the specified reference, or null if not found.
   */
  def getOrNull[T <: Model](modelRef: ModelRef[T]): T = getOrNull(modelRef.id)

  /**
   * Stores the specified model to the storage.
   * @return the id of the stored model.
   *         If the stored model did not yet have any id, a new one is generated and returned, as well as assigned to it.
   */
  def save[T <: Model](model: T): Long

}