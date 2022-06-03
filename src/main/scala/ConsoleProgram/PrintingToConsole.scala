package ConsoleProgram

import zio._
import zio.console._

class PrintingToConsole {
  def greet: ZIO[Console, Nothing, Unit] =
    for {
      name <- getStrLn.orDie
      _ <- putStrLn(s"Hello, $name!").orDie
    } yield ()
}
