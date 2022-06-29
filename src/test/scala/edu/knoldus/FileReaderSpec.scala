package edu.knoldus

import edu.knoldus.DecoderService.Decoder
import edu.knoldus.FileReaderService.FileReader
import edu.knoldus.TradesFilterService.TradesFilter
import zio.test.Assertion.equalTo
import zio.test._
import zio.{Has, ZLayer}

object FileReaderSpec extends DefaultRunnableSpec {
	val env: ZLayer[Any, Nothing, Has[FileReader.Service] with Has[Decoder.Service]] = FileReader.live ++ Decoder.live
	val env2: ZLayer[Any, Nothing, Has[FileReader.Service] with Has[Decoder.Service] with Has[TradesFilter.Service]] = env ++ TradesFilter.live

	override def spec: ZSpec[_root_.zio.test.environment.TestEnvironment, Any] = testSuite.provideSomeLayer(env2)

	val tradesData: Trades = Trades(List(Trade("ABC", 15.0), Trade("DEF", 16.0), Trade("KBC", 12.0), Trade("JBC", 17.0), Trade("XDC", 10.0), Trade("LCD", 15.1)))

	val filterTradesData: Seq[Trade] = List(Trade("DEF", 16.0), Trade("JBC", 17.0), Trade("LCD", 15.1))

	val testSuite: Spec[TradesFilter with Decoder with FileReader, TestFailure[Throwable], TestSuccess] = suite("Testing services")(
		testM("FileReader service get file data and Decoder service decodes file data to Trade type") {
			for {
				data <- FileReaderService.getFileData("file.json")
				trades <- DecoderService.decode(data)
			} yield assert(trades)(equalTo(tradesData))
		},
		testM("TradeFilterService filter the trades which have price greater than 15") {
			for {
				data <- FileReaderService.getFileData("file.json")
				trades <- DecoderService.decode(data)
				filterData <- TradesFilterService.filter(trades)
			} yield assert(filterData)(equalTo(filterTradesData))
		}
	)
}
