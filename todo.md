Todo
====


Next tasks:

* Network service that listens to incoming client connections, and allows them to log in and send messages to services.
  Use e.g. apache mina for networking.
* Annotation for service methods that accept client messages
* Message class for client messages - maybe use json?




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



