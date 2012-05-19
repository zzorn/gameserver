package org.skycastle.server

import registry.{RegistryImpl, Registry}
import utils.Logging


/**
 *
 */
object Server {

  var registry: Registry = null

  def main(args: Array[String]) {

    Logging.initializeLogging()

    // TODO: Read arguments

    // TODO: Load configuration?

    // Initialize context
    registry = new RegistryImpl()

    // Start
    registry.init()

    // TODO: Load any stored data


  }

}