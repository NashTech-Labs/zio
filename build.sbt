ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "monad-lifting"
  )

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "2.5.3",
  "dev.zio" %% "zio" % "2.0.0-RC6"
)