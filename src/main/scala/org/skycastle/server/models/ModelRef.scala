package org.skycastle.server.models

/**
 * A reference to a model object of some type.
 * Use the storage service to retrieve it.
 */
final case class ModelRef[T <: Model](id: Long)