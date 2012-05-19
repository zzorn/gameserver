package org.skycastle.server.services

/**
 * Interface for services.
 * Some services provide methods for clients to interact with the game.
 *
 * Services should be thread safe, their methods may be called from many threads at the same time.
 */
trait Service {

  def init()

  def shutdown()

}