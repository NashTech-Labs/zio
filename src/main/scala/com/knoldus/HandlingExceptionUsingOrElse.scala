package com.knoldus

import zio.{ExitCode, IO, URIO}
import scala.io.Source

object HandlingExceptionUsingOrElse extends zio.App {

  val fileReader = IO(Source.fromFile("Primary.txt")).orElse(IO(Source.fromFile("BackUpFile.txt")))

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    (for {
      source <- fileReader
      value <- IO(source.getLines().mkString("\n"))
      _ <- zio.console.putStrLn(value)
    } yield ()).exitCode
  }
}
