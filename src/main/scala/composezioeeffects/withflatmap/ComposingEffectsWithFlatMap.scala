package composezioeeffects.withflatmap

import zio.{ExitCode, Task, URIO, ZIO}

import scala.io.StdIn
import scala.util.Random

object ComposingEffectsWithFlatMap extends zio.App {

  def display(message: String): Task[Unit] = ZIO.effect(println(message))

  val getUserInput: Task[String] = ZIO.effect(StdIn.readLine())

  val generateRandomNumber: Task[Int] = ZIO.effect(Random.nextInt(5) + 1)

  // Example 1
  val program1: Task[Unit] = display("What is your name?").flatMap(_ =>
    getUserInput.flatMap(name =>
      display(s"Hello, $name!, Welcome to ZIO!"))
  )

  // Example 2
  val program2: Task[Unit] = generateRandomNumber.flatMap(randomNumber =>
    display("Guess a number from 1 to 5:").flatMap(_ =>
      getUserInput.flatMap(input =>
        if (input == randomNumber.toString) display("You guessed right!")
        else display(s"You guessed wrong, the number was $randomNumber!")))
  )

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = program1.exitCode
}
