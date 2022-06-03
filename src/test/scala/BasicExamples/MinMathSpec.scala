package BasicExamples
import zio.ZIO
import zio.test.Assertion._
import zio.test._

/**
 * Example for testing MinMath class
 * present under Basic Example Package
 */

object MinMathSpec extends DefaultRunnableSpec{
  val MathObject = new MinMath

  override def spec: Spec[Any, TestFailure[Nothing], TestSuccess] = suite("MinMathSpec")(
    /**
     * One way of doing things
     */
    testM("Addition works"){
      assertM(MathObject.add(1, 1))(equalTo(2))
    },

    testM("Still working"){
      assertM(MathObject.add(1,1))(equalTo(2))
    },
    /**
     * As you can see above I have
     * added a comma and started writing
     * another test in the same suite,
     * so that's how you can start
     * writing multiple tests under a same suite.
     * We can can also replace assertM with assert
     * using map or a for comprehension
     */

    testM("Using map function"){
      MathObject.add(1,1).map(x => assert(x)(equalTo(2)))
    },
    testM("Using for comprehension"){
      for {
        n <- MathObject.add(1,1)
      } yield assert(n)(equalTo(2))
    },
    /**
     * All of the above methods of
     * writing test cases are equivalent
     * to each other
     */

    /**
     * In the next example we will test out
     * divide function present in BasicExample
     * package under MinMath class
     */
    testM("testing a division that might throw exception"){
      for {
        exit <- ZIO.effect(MathObject.divide(1,0)).catchAll(_ => ZIO.fail(())).run
      } yield assert(exit)(fails(isUnit))
    },


  )
  /**
   * fails() <-  here is a type
   * of assertion that allow us to
   * assert an effect that
   * fails with a particular value
   *
   * Also note that isUnit is
   * equivalent to equalTo(())
   */
}