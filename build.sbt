name := "gameserver"

version := "0.1"

scalaVersion := "2.9.1"


resolvers += "Sonatype Public" at "https://oss.sonatype.org/content/groups/public/"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"


// Logging:
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.6.4"

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.6.4"

libraryDependencies += "log4j" % "log4j" % "1.2.16"


// Testing:
libraryDependencies += "org.scalatest" %% "scalatest" % "1.7.2" % "test"

// Config files:
libraryDependencies += "org.yaml" % "snakeyaml" % "1.11-SNAPSHOT"

// Networking
libraryDependencies += "org.apache.mina" % "mina-core" % "2.0.4"

// Password hashing
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"

// Storage
libraryDependencies += "redis.clients" % "jedis" % "2.0.0"

// Serialization
libraryDependencies += "com.google.code.gson" % "gson" % "2.2.1"


// Akka for parallel signals and messaging
libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.2"


