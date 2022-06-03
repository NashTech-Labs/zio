package ConsoleProgram

import zio.test.Assertion._
import zio.test.environment._
import zio.test.DefaultRunnableSpec
import zio.test._

/**
 * Reading and Writing back to the console
 * is pretty familiar with us, for a demo
 * try to understand greet function in Class
 * PrintingToConsole, that's what we are testing
 * here
 */

class PrintingToConsoleSpec extends DefaultRunnableSpec{
  val consoleProgram = new PrintingToConsole
  override def spec= suite("Console printing test")(
    testM("Says hello to user"){
      /**
       * To test Console driven program we use
       * Console service present in the environment
       * that contains methods like TestConsole, TestClock
       * TestRandom. We are using TestConsole here
       */
      for {
        _ <- TestConsole.feedLines("Average Joe") // feedLines function is getting something onto the console
        _ <- consoleProgram.greet
        value <- TestConsole.output // Storing output from console to the value
      } yield assert(value)(equalTo(Vector("Hello, Average Joe!\n")))
    }
  )
}
