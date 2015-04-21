name := "docker-timezone-test"

version := "1.0.0-SNAPSHOT"

lazy val dockerTimezoneTestProject = (project in file("."))
  .enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"  % "2.3.9",
  "io.spray"          %% "spray-json"  % "1.3.1",
  "org.scalatest"     %% "scalatest"   % "2.2.4" % Test
)

name in Universal := name.value