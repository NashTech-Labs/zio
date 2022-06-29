package edu.knoldus


import edu.knoldus.DatabaseService.Database
import edu.knoldus.Logging.Logger
import edu.knoldus.UserRepo.Users
import zio._
import zio.test.Assertion._
import zio.test._

import java.util.UUID

object LayerSpec extends DefaultRunnableSpec {

  val env: ZLayer[Any, Nothing, Has[Users.Service]] = (Database.live ++ Logger.live) >>> Users.live
  override def spec: ZSpec[_root_.zio.test.environment.TestEnvironment, Any] = testSuite.provideSomeLayer(env)


  val testSuite: Spec[Users, TestFailure[Throwable], TestSuccess] = suite("Testing services")(
    testM("UserRepo service get user data and prints to the console ") {
      for {
        id <- ZIO(UUID.randomUUID().toString)
        user <- UserRepo.getUser(id)
      } yield assert(user)(equalTo(User(id,"Akash")))
    }
  )
}
