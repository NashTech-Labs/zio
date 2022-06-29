package edu.knoldus

import edu.knoldus.DatabaseService.Database
import edu.knoldus.Logging.Logger
import edu.knoldus.UserRepo.Users
import zio._

import java.util.UUID

object Main extends zio.App {

  val program: ZIO[Users, Throwable, Unit] = for {
    id <- ZIO(UUID.randomUUID().toString)
    _ <- UserRepo.getUser(id)
  } yield ()

  val horizontalComposeLayer: ZLayer[Any, Nothing, Has[Database.Service] with Has[Logger.Service]] = Database.live ++ Logger.live

  val combinedLayer: ZLayer[Any, Nothing, Has[Users.Service]] = horizontalComposeLayer >>> Users.live

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = program.provideSomeLayer(combinedLayer).exitCode
}
