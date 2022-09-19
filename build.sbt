ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

val zioVersion = "2.0.2"
val zioConfigVersion = "3.0.1"


lazy val commonSettings = Seq(
  scalacOptions ++=Seq(
    "-Ymacro-annotations"
  )
)

lazy val root = (project in file("."))
  .settings(
    name := "zio-http-api",
    commonSettings,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "com.beachape" %% "enumeratum" % "1.6.0",
      "io.d11" %% "zhttp" % "2.0.0-RC11" ,
      "dev.zio" %% "zio-json" % "0.3.0-RC11",
      "dev.zio" %% "zio-config" % "3.0.1",
      "dev.zio" %% "zio-macros" % "2.0.2",
      "dev.zio" %% "zio-config" % "3.0.1",
      "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
      "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
      "dev.zio" %% "zio-test" % "2.0.2" % Test

    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
