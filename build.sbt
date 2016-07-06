name := """siroop-el"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"



libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,

  "org.elasticsearch"              %  "elasticsearch"               % "2.3.0",
  "com.sksamuel.elastic4s"         %% "elastic4s-core"              % "2.3.0",
  "com.sksamuel.elastic4s"         %% "elastic4s-jackson"           % "2.3.0",
  "com.sksamuel.elastic4s"         %% "elastic4s-testkit"           % "2.3.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
