package com.knoldus

import zio.{ExitCode, URIO, ZIO}

object HandlingExceptionUsingEither extends zio.App {

  val zEither: URIO[Any, Either[String, Nothing]] = ZIO.fail("I am failing you.").either

  val zMatch: ZIO[Any, Nothing, String] = zEither.map {
    case Right(success) => success
    case Left(exception) => s"Oops it failed with an exception: $exception"
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    (for {
      value <- zMatch
      _ <- zio.console.putStrLn(value)

    } yield ()).exitCode
  }
}
