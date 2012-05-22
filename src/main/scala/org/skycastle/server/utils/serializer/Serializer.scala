package org.skycastle.server.utils.serializer

/**
 *
 */
trait Serializer {

  def serialize(obj: AnyRef): Array[Byte]

  def deserialize[T](data: Array[Byte])(implicit kind: Class[T]): T


}
