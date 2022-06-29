package edu.knoldus

import zio.json._

case class Trades(trades: List[Trade])

case class Trade(symbol: String, price: Double)


object Trades {
	implicit val decoder: JsonDecoder[Trades] =
		DeriveJsonDecoder.gen[Trades]
	implicit val eecoder: JsonEncoder[Trades] =
		DeriveJsonEncoder.gen[Trades]
}

object Trade {
	implicit val decoder: JsonDecoder[Trade] =
		DeriveJsonDecoder.gen[Trade]

	implicit val eecoder: JsonEncoder[Trade] =
		DeriveJsonEncoder.gen[Trade]
}
