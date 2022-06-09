package SimpleServer

import data.ListData
import zhttp.http._
import zhttp.service.Server
import zio._

import scala.io.Source.fromURL

/**
 * Start the server either by sbt run, Or by running this singleton (SimpleServerApp)
 * we are trying to display 2 type of data one is normal text form, and the other one
 * is json data coming from a open public api provided by binance.com, check how data is
 * propagating in both of the scenarios
 */
object SimpleServerApp extends App {

  // the line below is just collecting the data in a json format, converting it into a string for further use
  lazy val jsonData: String = fromURL("https://api2.binance.com/api/v3/ticker/24hr").mkString
  // the getList function will return a simple list of string values
  val textData: List[String] = ListData.getList
  val app : HttpApp[Any, Nothing] = Http.collect[Request] {
    // working with some simple data that is a List[String]
    case Method.GET -> _ / "data" => Response.text(ListData.getList.mkString("."))
    case Method.GET -> zhttp.http.Path() => Response.text("Hello Knolder") // this is the basic route (Landing Page)
    /**
     * This post route can either be checked on postman by hitting a post request on localhost:8090/binanceData
     * or else you can use that get route (localhost:8090/text)
     * which is just showing us the jsonData into a text format
     */
    case Method.POST -> _ / "binanceData" => Response.json(jsonData) // this is how we are handling json
    case Method.GET -> _ / "text" => Response.text(jsonData) // this is how we are handling text
    /**
     * note that above we are using Response.text
     * for string type of data and Response.json
     * for json type of data. Rest is the same for all
     */
    case Method.GET -> _ / "json" => Response.json("""{"greetings" : "Hello World!"}""")
    case Method.POST -> _ / "json" => Response.json("""{"greetings": "Hello World!"}""")
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    Server.start(8090, app).exitCode
  }
}
