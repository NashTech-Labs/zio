package composezioeeffects.withforcomprehension

import zio.{ExitCode, Task, URIO, ZIO}
import scala.io.StdIn
import scala.util.Random

object ComposingEffectsWithForComprehension extends zio.App {
  def display(message: String): Task[Unit] = ZIO.effect(println(message))

  val getUserInput: Task[String] = ZIO.effect(StdIn.readLine())

  val generateRandomNumber: Task[Int] = ZIO.effect(Random.nextInt(5) + 1)

  // Example 1
  val program1: Task[Unit] = for {
    _ <- display("What is your name?")
    name <- getUserInput
    _ <- display(s"Hello, $name!, Welcome to ZIO!")
  } yield ()

  // Example 2
  val program2: Task[Unit] = for {
    randomNumber <- generateRandomNumber
    _ <- display("Guess a number from 1 to 5:")
    input <- getUserInput
    _ <- if (input == randomNumber.toString) display("You guessed right!")
    else display(s"You guessed wrong, the number was $randomNumber!")
  } yield ()

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = program2.exitCode
}
