package org.skycastle.server.models

import org.skycastle.server.utils.ParameterChecker

/**
 * A data object describing some object in the game.
 * Serialized to data storage with some POJO serializer.
 *
 * Initially has an id of zero, will be assigned an unique id before being stored.
 * Once it has a non-zero id, it can not be changed anymore.
 */
trait Model {

  private var _id = 0L

  /**
   * @return unique id for this model instance, or zero if it has not yet been stored in the data storage.
   */
  final def id: Long = _id

  /**
   * Used to set the id of the model instance.  Called by the storage service.
   */
  final def setId(newId: Long) {
    ParameterChecker.requireNotNull(newId, 'newId)
    if (_id != 0) throw new Error("Attempt to set a new id '"+newId+"' to a model with an existing non zero id '"+_id+"'")

    _id = newId
  }


}