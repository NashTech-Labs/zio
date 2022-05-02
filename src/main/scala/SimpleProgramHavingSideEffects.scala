import scala.io.StdIn.readLine
import scala.util.Random

object SimpleProgramHavingSideEffects extends App {

  println("What's your name?")
  val name: String = readLine()
  println(s"Hi $name, Welcome to this game!")

  var exec: Boolean = true

  while (exec) {
    val num: Int = Random.nextInt(10) + 1

    println("Please guess a number from 1 to 10")
    val guessedNum: Int = readLine().toInt

    if (guessedNum == num) println(s"Congrats $name! You guessed it right")
    else println(s"You guessed it wrong, $name. The number was $num")

    println("Do you want to continue?")

    readLine() match {
      case "y" => exec = true
      case "n" => exec = false
    }
  }

}
