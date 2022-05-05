ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "Wrap-Side-Effects-As-Pure-Values-Using-IO-Monad"
  )

libraryDependencies += "org.typelevel" %% "cats-effect" % "2.5.3"