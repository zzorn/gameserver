package org.skycastle.server.models

import entity.Entity

/**
 * A reference to a model object of some type.
 * Use the storage service to retrieve it.
 */
trait Ref[T <: Model] {
  def id: Long
}

/**
 * A reference to a model object of some type.
 * Use the storage service to retrieve it.
 */
case class ModelRef[T <: Model](id: Long) extends Ref[T]


case class EntityId(id: Long) extends Ref[Entity]

case object UndefinedId extends EntityId(0)