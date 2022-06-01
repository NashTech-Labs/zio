package com.knoldus

import zio.{ExitCode, IO, URIO, ZIO}
import java.io.FileNotFoundException
import scala.io.{BufferedSource, Source}

object HandlingExceptionUsingCatchSome extends zio.App {

  val FileReader: ZIO[Any, Throwable, BufferedSource] = IO(Source.fromFile("PrimaryFile.txt")).catchSome {
    case _: FileNotFoundException => IO(Source.fromFile("BackUpFile.txt"))
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    (for {
      source <- FileReader
      value <- IO(source.getLines().mkString("\n"))
      _ <- zio.console.putStrLn(value)
    } yield ()).exitCode
  }
}
