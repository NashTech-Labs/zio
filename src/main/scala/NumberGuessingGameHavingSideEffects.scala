import scala.io.StdIn.readLine
import scala.util.Random

object NumberGuessingGameHavingSideEffects extends App {

  println("What's your name?") // printing to console - side-effect
  val name: String = readLine() // reading from console - side-effect
  println(s"Hi $name, Welcome to this game!")

  var exec: Boolean = true

  while (exec) {
    val num: Int = Random.nextInt(10) + 1 // generating random data - side-effect

    println("Please guess a number from 1 to 10")
    val guessedNum: Int = readLine().toInt // may throw exception as its a partial function

    if (guessedNum == num) println(s"Congrats $name! You guessed it right")
    else println(s"You guessed it wrong, $name. The number was $num")

    println("Do you want to continue?")

    readLine() match {    // may throw exception as its a partial function
      case "y" => exec = true
      case "n" => exec = false
    }
  }

}
