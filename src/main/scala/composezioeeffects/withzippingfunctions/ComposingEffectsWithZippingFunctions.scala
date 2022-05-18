package composezioeeffects.withzippingfunctions

import zio.console.Console.Service.live.putStrLn
import zio.{Task, ZIO}

import scala.io.StdIn

object ComposingEffectsWithZippingFunctions {
  val getFirstName: Task[String] =
    ZIO.effect(StdIn.readLine("What is your first name?"))

  val getLastName: Task[String] =
    ZIO.effect(StdIn.readLine("What is your last name"))

  val getAge: Task[Int] =
    ZIO.effect(StdIn.readLine("What is your age").toInt)

  val getEmail: Task[String] =
    ZIO.effect(StdIn.readLine("What is your email?"))

  // example - zip
  val emailAndAge: Task[(String, Int)] = getEmail.zip(getAge)

  // example - zipLeft
  val firstName: Task[String] = getFirstName.zipLeft(putStrLn("Nice!"))

  // example - zipRight
  val lastName: Task[String] = putStrLn("Hello!").zipRight(getLastName)

  // example - zipWith
  val fullName: Task[String] =
    getFirstName.zipWith(getLastName)((first, last) => s"$first $last")
}
