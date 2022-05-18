package composezioeeffects.withforeachandcollectall

import zio.console.{Console, getStrLn, putStrLn}
import zio.{ExitCode, URIO, ZIO}
import java.io.IOException

object ComposingEffectsWithForeachAndCollectAll extends zio.App {

  // example - foreach
  def printAllElements[A](elements: List[A]): ZIO[Console, IOException, List[Unit]] =
    ZIO.foreach(elements) { element =>
      putStrLn(element.toString)
    }

  // example - collectAll
  def collectAllResults(listOfEffects: List[ZIO[Console, IOException, String]]): ZIO[Console, IOException, List[String]] =
    ZIO.collectAll(listOfEffects)

  val listOfEffects: List[ZIO[Console, IOException, String]] = List(
    putStrLn("What is your name?") zipRight getStrLn,
    putStrLn("your age?") zipRight getStrLn,
    putStrLn("your department?") zipRight getStrLn,
  )

  val program = for {
    _ <- printAllElements(List("Welcome", "to", "ZIO!"))
    results <- collectAllResults(listOfEffects)
    _ <- putStrLn(s"Results: $results")
  } yield ()

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = program.exitCode
}
