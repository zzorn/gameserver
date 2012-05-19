package org.skycastle.server.services.storage

import org.skycastle.server.models.entity.Entity
import org.skycastle.server.models.{EntityId, Ref, Model}
import org.skycastle.server.services.Service
import org.skycastle.server.models.account.Account


/**
 * Provides persistent storage of entities and models.
 *
 * Thread safe.
 */
trait StorageService extends Service {

  /**
   * @return the model with the specified id.  Throws an exception if not found.
   */
  def get[T <: Model](id: Long): T = {
    val value: T = getOrNull[T](id)
    if (value == null) throw new Error("No reference found for model with id '"+id+"'")
    else value
  }

  def getEntity(id: Long): Entity = get[Entity](id)
  def getEntity(entityId: EntityId): Entity = get[Entity](entityId.id)

  /**
   * @return the model with the specified id.  Throws an exception if not found.
   */
  def getOrNull[T <: Model](id: Long): T

  /**
   * @return the model with the specified reference.  Throws an exception if not found.
   */
  def get[T <: Model](modelRef: Ref[T]): T = get(modelRef.id)

  /**
   * @return the model with the specified reference, or null if not found.
   */
  def getOrNull[T <: Model](modelRef: Ref[T]): T = getOrNull(modelRef.id)

  /**
   * Stores the specified model to the storage.
   * @return the id of the stored model.
   *         If the stored model did not yet have any id, a new one is generated and returned, as well as assigned to it.
   */
  def save[T <: Model](model: T): Long


  /**
   * Saves a new account.
   * @param accountName the account name to use.
   * @param account the account to save.
   * @return true if the accountName was free, false if the account name was in use.
   */
  def saveNewAccount(accountName: String, account: Account): Boolean

  /**
   * Updates the stored account with the specified name, with a new object.
   */
  def updateAccount(accountName: String, account: Account)

  /**
   * @return the account with the specified name, or null if not found.
   */
  def loadAccount(accountName: String): Account

  /**
   * @return true if there exists an account with the specified name.
   */
  def accountExists(accountName: String): Boolean


}