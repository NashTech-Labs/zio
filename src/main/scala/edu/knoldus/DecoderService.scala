package edu.knoldus

import zio._
import zio.json._

object DecoderService {
	type Decoder = Has[Decoder.Service]

	object Decoder {
		trait Service {
			def decode(data: String): ZIO[Any, Throwable, Trades]
		}

		val live: ULayer[Has[Service]] = ZLayer.succeed {
			(data: String) => {
				ZIO.fromEither(data.fromJson[Trades].left.map(errorMessage => new RuntimeException(errorMessage)))
			}
		}
	}

	def decode(data: String): ZIO[Decoder, Throwable, Trades] = ZIO.accessM[Decoder](_.get.decode(data))
}
