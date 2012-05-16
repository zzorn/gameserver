package org.skycastle.server.models.entity

import java.lang.reflect.Method
import scala.collection.JavaConversions._
import org.skycastle.server.utils.StringUtils

/**
 *
 */
trait Ability {

  def getActionMethods: Map[Symbol, Method] = {
    (getClass.getMethods.toList flatMap {method =>
      val actionAnnotation: Action = method.getAnnotation(classOf[Action])
      if (actionAnnotation != null) {
        val value = actionAnnotation.value()
        val name = if (StringUtils.isIdentifier(value)) Symbol(value) else Symbol(method.getName)
        Some((name, method))
      }
      else {
        None
      }
    }).toMap
  }

}