scalaVersion := "2.12.7"
organization := "com.example"

lazy val `unit-testing-presentation` = (project in file("."))
    .settings(
        name := "Unit testing presentation"
    )

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.21",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.21" % Test,
  "org.mockito" % "mockito-core" % "2.27.0" % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "org.specs2" %% "specs2-core" % "4.3.4" % Test
)
