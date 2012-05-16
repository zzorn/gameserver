package org.skycastle.server.models.entity.abilities

/**
 * Entity is a piece of clothing, or can be worn as one
 */
class Wearable {

  var slot: Symbol = null

  // TODO: Size it fits for

  /**
   * Temperature insulation.
   */
  // TODO: What physical unit is it measured in?
  var insulation: Double = 0.0

  /**
   * Size of holes in the garment, allowing gases/liquids/grains in or out
   */
  var meshSize_m: Double = 0.001

  // TODO: Pressure resistance
  // TODO: How tightly it seals around edges


  // Armoring / protection from different types of damage
  var pierceArmor: Double = 0.0
  var bashArmor: Double = 0.0
  var cutArmor: Double = 0.0

  // var texture: String = null

}
