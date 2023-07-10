ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "common_code_queue"
  )

libraryDependencies ++= List(
  "com.softwaremill.sttp.client3" %% "core" % "3.8.15",
  "com.slack.api" % "bolt-jetty" % "1.29.0",
  "com.typesafe.play" %% "play-json" % "2.9.4",
  "com.slack.api" % "slack-api-client" % "1.11.0",
  "org.slf4j" % "slf4j-api" % "1.7.32",
  "ch.qos.logback" % "logback-classic" % "1.2.6",
)