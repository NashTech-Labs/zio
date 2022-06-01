package com.knoldus

import zio.{ExitCode, IO, URIO, ZIO}
import scala.io.{BufferedSource, Source}

object HandlingExceptionUsingRetry extends zio.App {

  val fileReader: ZIO[Any, Throwable, BufferedSource] = {
    IO(Source.fromFile("Primary.txt"))
      .retryN(3)
      .orElse(IO(Source.fromFile("BackUpFile.txt")))
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    (for {
      source <- fileReader
      value <- IO(source.getLines().mkString("\n"))
      _ <- zio.console.putStrLn(value)
    } yield ()).exitCode
  }
}
