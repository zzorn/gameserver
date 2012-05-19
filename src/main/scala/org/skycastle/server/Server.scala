package org.skycastle.server

import registry.{RegistryImpl, Registry}
import utils.Logging


/**
 *
 */
object Server {

  def main(args: Array[String]) {

    Logging.initializeLogging()

    // TODO: Read arguments

    // TODO: Load configuration?

    // Initialize context
    val registry: Registry = new RegistryImpl()

    // Start
    registry.init()

    // TODO: Load any stored data


  }

}