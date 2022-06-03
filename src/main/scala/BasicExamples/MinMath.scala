package BasicExamples

import zio.ZIO

class MinMath {
  def add(numOne: Int, numTwo: Int) : ZIO[Any, Nothing, Int] = ZIO.succeed(numOne + numTwo)
  def divide(dividend: Int, divisor:Int): Int = dividend / divisor
}
