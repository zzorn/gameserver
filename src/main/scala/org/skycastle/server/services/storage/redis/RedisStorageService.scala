package org.skycastle.server.services.storage.redis

import org.skycastle.server.services.storage.StorageService
import org.skycastle.server.registry.Registry
import org.skycastle.server.utils.serializer.{GsonSerializer, Serializer}
import org.skycastle.server.models.account.User
import org.skycastle.server.models.Model
import org.skycastle.server.utils.ParameterChecker
import java.nio.charset.Charset
import redis.clients.jedis.{JedisPoolConfig, JedisPool, Protocol, Jedis}

/**
 * StorageService implementation that uses the Redis no-sql database.
 */
class RedisStorageService(registry: Registry,
                          redisAddress: String = "localhost",
                          redisPort: Int = Protocol.DEFAULT_PORT,
                          redisTimeout: Int = Protocol.DEFAULT_TIMEOUT) extends StorageService {

  private var jedisPool: JedisPool = null
  private val serializer: Serializer = new GsonSerializer()
  private val utf8Charset: Charset = Charset.forName("UTF-8")

  def init() {
    jedisPool = new JedisPool(new JedisPoolConfig(), redisAddress, redisPort, redisTimeout)
  }

  def shutdown() {
    jedisPool.destroy()
  }

  /**
   * @return the model with the specified id.  Throws an exception if not found.
   */
  def getOrNull[T <: Model](id: Long)(implicit kind: Class[T]): T = {
    ParameterChecker.requireNotNull(id, 'id)

    val jedis = jedisPool.getResource
    val data: Array[Byte] = jedis.get(keyForId(id))
    jedisPool.returnResource(jedis)

    if (data == null) null.asInstanceOf[T]
    else serializer.deserialize(data)(kind)
  }

  /**
   * Stores the specified model to the storage.
   * @return the id of the stored model.
   *         If the stored model did not yet have any id, a new one is generated and returned, as well as assigned to it.
   */
  def save[T <: Model](model: T): Long = {
    ParameterChecker.requireNotNull(model, 'model)

    val jedis = jedisPool.getResource
    if (model.id == 0) {
      model.setId(jedis.incr("NextFreeId"))
    }

    val data: Array[Byte] = serializer.serialize(model)
    jedis.set(keyForId(model.id), data)
    jedisPool.returnResource(jedis)

    model.id
  }

  /**
   * Saves a new account.
   * @param accountName the account name to use.
   * @param account the account to save.
   * @return true if the accountName was free, false if the account name was in use.
   */
  def saveNewAccount(accountName: String, account: User): Boolean = {
    val data: Array[Byte] = serializer.serialize(account)

    val jedis = jedisPool.getResource
    val result: Long = jedis.setnx(keyForAccount(accountName), data)
    val wasFree = result == 0L
    jedisPool.returnResource(jedis)

    wasFree
  }

  /**
   * Updates the stored account with the specified name, with a new object.
   */
  def updateAccount(accountName: String, account: User) {
    val data: Array[Byte] = serializer.serialize(account)
    val jedis = jedisPool.getResource
    jedis.set(keyForAccount(accountName), data)
    jedisPool.returnResource(jedis)
  }

  /**
   * @return the account with the specified name, or null if not found.
   */
  def loadAccount(accountName: String): User = {
    val jedis = jedisPool.getResource
    val data: Array[Byte] = jedis.get(keyForAccount(accountName))
    jedisPool.returnResource(jedis)

    if (data == null) null
    else serializer.deserialize(data)(classOf[User])
  }

  /**
   * @return true if there exists an account with the specified name.
   */
  def accountExists(accountName: String): Boolean = {
    val jedis = jedisPool.getResource
    val exists: Boolean = jedis.exists(keyForAccount(accountName))
    jedisPool.returnResource(jedis)
    exists
  }

  private def keyForId(id: Long): Array[Byte] = {
    ParameterChecker.requireNotZero(id.doubleValue(), 'id)
    ("id#"+id).getBytes(utf8Charset)
  }

  private def keyForAccount(accountName: String): Array[Byte] = {
    ParameterChecker.requireIsIdentifier(accountName, 'accountName)
    ("account#"+accountName).getBytes(utf8Charset)
  }


}
