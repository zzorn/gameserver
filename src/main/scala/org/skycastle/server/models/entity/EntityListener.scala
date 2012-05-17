package org.skycastle.server.models.entity

/**
 *
 */
trait EntityListener {

  def onAbilityChanged(entity: Entity, abilityName: Symbol, oldAbility: Ability, newAbility: Ability)

}