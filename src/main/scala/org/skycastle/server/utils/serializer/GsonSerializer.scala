package org.skycastle.server.utils.serializer

import com.google.gson.Gson
import java.nio.charset.Charset

/**
 * Serializes data to utf encoded json format.
 */
// TODO: Add compression?
class GsonSerializer extends Serializer {

  private val gson: Gson = new Gson()
  private val utf8Charset: Charset = Charset.forName("UTF-8")

  def serialize(obj: AnyRef): Array[Byte] = {
    val json = gson.toJson(obj)
    json.getBytes(utf8Charset)
  }

  def deserialize[T](data: Array[Byte])(implicit kind: Class[T]): T = {
    if (data == null) null.asInstanceOf[T]
    else {
      val json = new String(data, utf8Charset)

      if (json == null || json == "null") null.asInstanceOf[T]
      else gson.fromJson(json, kind)
    }
  }
}
