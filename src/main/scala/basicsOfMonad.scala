import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success
object basicsOfMonad {

  case class SafeValue[+T](private val internalValue: T) {  //"constructor" = pure, or unit
    def get: T = synchronized {
      internalValue
    }
    def transform[S](transformer: T => SafeValue[S]): SafeValue[S] = synchronized { // bind, or flatMap
      transformer(internalValue)
    }
  }

  def gimmeSafeValue[T](value: T): SafeValue[T] = SafeValue(value)
  val safeString: SafeValue[String] = gimmeSafeValue("Scala is awesome") // obtained from elsewhere
  //  extract
  val string = safeString.get
  //  transform
  val upperString = string.toUpperCase()
  //  wrap
  val upperSafeString = SafeValue(upperString)
  //  ETW

  //  compressed:
  val upperSafeString2 = safeString.transform(string => SafeValue(string.toUpperCase()))

  // Example 1: A census application
  case class Person(firstName: String, lastName: String) {
    // you have a requirement that these fields must not be nulls
    assert(firstName != null && lastName != null)
  }

  def getPerson(firstName: String, lastName: String): Person =
    if (firstName != null) {
      if (lastName != null) {
        Person(firstName.capitalize, lastName.capitalize)
      } else {
        null
      }
    } else {
      null
    }
  def getPersonBetter(firstName: String, lastName: String): Option[Person] =
    Option(firstName).flatMap { fName =>
      Option(lastName).flatMap { lName =>
        Option(Person(fName, lName))
      }
    }

  def getPersonFor(firstName: String, lastName: String): Option[Person] = for {
    fName <- Option(firstName)
    lName <- Option(lastName)
  } yield Person(fName, lName)

  //Example 2: asynchronous fetches
  case class User(id: String)
  case class Product(sku: String, price: Double)
  // ^ never use Double for currency IN YOUR LIFE

  def getUser(url: String): Future[User] = Future {
    User("Nikhil") // sample implementation
  }
  def getLastOrder(userId: String): Future[Product] = Future {
    Product("123-456", 99.09) //sample
  }
  val monadeURL = "my.store.com/users/daniel"
  getUser(monadeURL).onComplete{
    case Success(User(id)) =>
      val lastOrder = getLastOrder(id)
      lastOrder.onComplete({
        case Success(Product(sku, p)) =>
          val vatIncludedPrice = p*1.19
      })
  }
  val vatInclPrice: Future[Double] = getUser(monadeURL)
    .flatMap(user => getLastOrder(user.id))
    .map(_.price * 1.19)

  val vatInclPriceFor = for{
    user <- getUser(monadeURL)
    product <- getLastOrder(user.id)
  } yield product.price * 1.19

  //Example 3: double-for “loops”
  val numbers = List(1,2,3)
  val chars = List('a', 'b', 'c')

  val checkerboard = numbers.flatMap(number => chars.map(char => (number, char)))

  val checkerboard2 = for {
    n <- numbers
    c <- chars
  } yield (n, c)

  //  Properties

  //  prop 1
  def twoConsecutive(x: Int) = List(x, x+1)
  twoConsecutive(3) // List(3,4)
  List(3).flatMap(twoConsecutive) // List(3,4)
  //  Monad(x).flatMap(f) == f(x)

  //  prop 2
  List(1,2,3).flatMap(x => List(x)) //List(1,2,3)
  //  Monad(v).flatMap(x => Monad(x)) USELESS, return Monad(v)

  //  prop 3 -  ETW-ETW
  val incrementer = (x: Int) => List(x, x+1)
  val doubler = (x: Int) => List(x, x*2)

  def main(args: Array[String]): Unit = {
    println(
      numbers.flatMap(incrementer).flatMap(doubler),
      numbers.flatMap(x => incrementer(x).flatMap(doubler)), numbers.flatMap(incrementer).flatMap(doubler) ==
      numbers.flatMap(x => incrementer(x).flatMap(doubler))
    )
  }
  // List(1,2,2,4, 2,4,3,6,  3,6,4,8)

  /*
  *       List(
  *             incrementer(1).flatMap(doubler) -- 1,2,2,4
  *             incrementer(2).flatMap(doubler) -- 2,4,3,6
  *             incrementer(3).flatMap(doubler) -- 3,6,4,8
  * */

}
