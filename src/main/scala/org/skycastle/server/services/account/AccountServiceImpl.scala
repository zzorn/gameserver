package org.skycastle.server.services.account

import org.skycastle.server.registry.Registry
import org.skycastle.server.models.entity.Entity
import org.skycastle.server.models.account.User
import org.skycastle.server.models.{EntityId, ModelRef, Ref}
import org.skycastle.server.models.entity.abilities.{Located, Physical}

/**
 *
 */
class AccountServiceImpl(context: Registry) extends AccountService {


  def initializeNewAccount(user: User) {
    // Do any needed account initialization here, other than name and password

    // Create initial test char
    createNewCharacter(user)
  }

  def createNewCharacter(user: User): EntityId = {

    val character = new Entity()
    initCharacter(character)

    var characterId: EntityId = context.storageService.saveEntity(character)
    user.characters ::= characterId
    user.currentCharacter = characterId
    characterId
  }

  private def initCharacter(entity: Entity) {
    entity.setAbility('physical, new Physical())
    entity.setAbility('located, new Located())

    // TODO: Place in starting position and environment
  }

  def init() {}

  def shutdown() {}

}