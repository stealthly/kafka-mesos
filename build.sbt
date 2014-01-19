
name := "kafka-mesos"

version := "0.1.0.0"

scalaVersion := "2.10.3"

mainClass := Some("Stub")

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "2.2.2" % "test",
  "org.apache.kafka" % "kafka_2.10" % "0.8.0" intransitive(),
  "log4j" % "log4j" % "1.2.17",
  "org.apache.mesos" % "mesos" % "0.15.0",
  "com.google.protobuf" % "protobuf-java" % "2.4.1"
)
