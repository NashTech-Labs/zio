
val zioVersion = "1.0.12"
val scalaVer = "2.13.8"

lazy val settings = Seq(
  name := "zio-json-filtering",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := scalaVer,
  libraryDependencies ++=Seq(
    "dev.zio" %% "zio" % zioVersion,
    "dev.zio"          %% "zio-test"         % zioVersion % "test",
    "dev.zio"          %% "zio-test-sbt"     % zioVersion % "test",
    "dev.zio" %% "zio-json"    % "0.1.5"
  )
)
lazy val root = (project in file("."))
  .settings(settings)

