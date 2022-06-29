package edu.knoldus

import edu.knoldus.DatabaseService.Database
import edu.knoldus.Logging.Logger
import zio._

object UserRepo {
  type Users = Has[Users.Service]

  object Users {
    trait Service {
      def getUser(id: String): Task[User]
    }

    val any: ZLayer[Users, Nothing, Users] =
      ZLayer.requires[Users]

    val live: ZLayer[Has[Database.Service] with Has[Logger.Service], Nothing, Has[Service]] =
      ZLayer.fromServices[Database.Service, Logger.Service, Service] { (database, logger) =>
        new Service {
          override def getUser(id: String): Task[User] = for {
            user <- database.getUser(id)
            _ <- logger.log(s"Hello $user")
          } yield user
        }
      }
  }

  def getUser(id: => String): ZIO[Users, Throwable, User] =
    ZIO.accessM(_.get.getUser(id))
}
