ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "zio-http-simple-server"
  )

val zioVersion = "1.0.14"
val ZHTTPVersion = "1.0.0.0-RC27"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-test" % zioVersion,
  "dev.zio" %% "zio-test-sbt" % zioVersion,
  "io.d11" %% "zhttp"      % ZHTTPVersion,
  "io.d11" %% "zhttp-test" % ZHTTPVersion % Test,
)