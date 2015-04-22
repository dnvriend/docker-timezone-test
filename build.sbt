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
  "codes.reactive"    %% "scala-time"  % "0.3.0-SNAPSHOT",
  "com.typesafe.akka" %% "akka-actor"  % "2.3.9",
  "io.spray"          %% "spray-json"  % "1.3.1",
  "org.scalatest"     %% "scalatest"   % "2.2.4" % Test
)

resolvers ++= Seq (
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
  "oss nexus" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

name in Universal := name.value