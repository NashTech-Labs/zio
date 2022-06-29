ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"
val zioVersion = "1.0.15"
lazy val root = (project in file("."))
  .settings(
    name := "zlayer-composition",
    libraryDependencies ++=Seq(
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio"          %% "zio-test"         % zioVersion % "test",
      "dev.zio"          %% "zio-test-sbt"     % zioVersion % "test",
      "dev.zio" %% "zio-json"    % "0.1.5"
    )
  )
