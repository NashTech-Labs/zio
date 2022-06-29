package edu.knoldus

import zio.{Has, ULayer, ZIO, ZLayer}

object TradesFilterService {
	type TradesFilter = Has[TradesFilter.Service]

	object TradesFilter {
		trait Service {
			def filter(data: Trades): ZIO[Any, Throwable, List[Trade]]
		}

		val live: ULayer[Has[Service]] = ZLayer.succeed {
			(data: Trades) => {
				ZIO.effect(data.trades.filter(trade => trade.price > 15))
			}
		}
	}

	def filter(data: Trades): ZIO[TradesFilter, Throwable, List[Trade]] = ZIO.accessM[TradesFilter](_.get.filter(data))
}

