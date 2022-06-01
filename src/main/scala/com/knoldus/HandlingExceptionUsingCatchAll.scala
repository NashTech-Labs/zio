package com.knoldus

import zio.{ExitCode, IO, URIO, ZIO}

object HandlingExceptionUsingCatchAll extends zio.App {

  val zFailed: IO[String, Nothing] = ZIO.fail("Some failure")

  val zCatchAll: ZIO[Any, Nothing, String] = zFailed.catchAll { _ =>
    ZIO.succeed("I will catch you from fail.")
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    (for {
      value <- zCatchAll
      _ <- zio.console.putStrLn(value)
    } yield ()).exitCode
  }
}
