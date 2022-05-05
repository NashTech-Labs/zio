import cats.effect.IO
import scala.io.StdIn.readLine
import scala.util.{Random, Try}

object WrappingSideEffectsUsingIOMonad extends App {

  def putStrLn(message: String): IO[Unit] = IO {
    // wrapping side-effect inside IO Monad
    println(message)
  }

  def getStrLn: IO[String] = IO {
    readLine()
  }

  def randomNumber(upper: Int): IO[Int] = IO {
    Random.nextInt(upper)
  }

  def parseNumber(str: String): Option[Int] = Try(str.toInt).toOption // this converts our partial function to total function

  def checkContinue(name: String): IO[Boolean] = for {  // Using for-comprehension to compose two or more IO Monads guarantees sequential evaluation.
    _ <- putStrLn(s"Do you want to continue $name?")
    input <- getStrLn
    cont <- input.toLowerCase() match {
      case "y" => IO(true)
      case "n" => IO(false)
      case _ => checkContinue(name)
    }
  } yield cont

  def gameLoop(name: String): IO[Unit] = for {
    num <- randomNumber(10).map(_ + 1)
    _ <- putStrLn("Please guess a number from 1 to 10")
    input <- getStrLn
    _ <- parseNumber(input) match {
      case None => putStrLn("Not a number!")
      case Some(guessedNum) =>
        if (guessedNum == num) putStrLn(s"Congrats $name! You guessed it right")
        else putStrLn(s"You guessed it wrong, $name. The number was $num")
    }
    cont <- checkContinue(name)
    _ <- if (cont) gameLoop(name) else IO.unit
  } yield ()

  val myGameLogic: IO[Unit] = for {
    _ <- putStrLn("What's your name?")
    name <- getStrLn
    _ <- putStrLn(s"Hi $name, Welcome to this game!")
    _ <- gameLoop(name)
  } yield ()

  // running the side-effects wrapped inside IO
  myGameLogic.unsafeRunSync()
}
