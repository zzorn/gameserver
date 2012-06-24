Todo
====


Next tasks:

* Merge account handling
* Annotation for actors that accept client messages, and case classes that can be used as client messages
* Avatars observing other entities around them
* Generate some resource entities in the world
* Pickup, inventory
* Claim building lot
* Create worksite for project
* Contribute work to project
* Machines / processors that modify resources, possibly needing work or other consumables


Functions provided by server for client:
====

Connecting
* Negotiate protocol (json / zipped json)

Account
* CreateAccount(login, email, password)
* Login(login, pw)
* Reset password(email / login)
* CreateCharacter(properties): charId
* PlayCharacter(charId)
* StopPlayingCharacter(charId)
* AddFunds(): btcAddress


Environment
* getMovementModel: movementModel
* move(params)
* getPerceptinModel: PerceptionModel
* getPerceptions
* start/stop receiving environment perceptions
* modifyEnvironment


Inventory
* MoveItem(item, targetContainer)
* getAccessibleContainers


Chat
* getChannels
* createChannel?
* say(channel, message)


Crafting / modifying / building

Combat

Organizations
* create
* join/leave
* modify


WorldEditing (for admin users)
* modify environment
* modify biotopes
* modify creatures
* modify settings



