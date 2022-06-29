package edu.knoldus

import edu.knoldus.DecoderService.Decoder
import edu.knoldus.FileReaderService.FileReader
import edu.knoldus.TradesFilterService.TradesFilter
import zio.{ExitCode, URIO, ZIO}
import zio.console.{Console, putStrLn}

object Main extends zio.App {
	val program: ZIO[Console with FileReader with Decoder with TradesFilter, Throwable, Unit] = for {
		data <- FileReaderService.getFileData("file.json")
		trades <- DecoderService.decode(data)
		_ <- putStrLn(s"Unfiltered Trades Data:- ${trades.toString}")
		filterTrades <- TradesFilterService.filter(trades)
		_ <- putStrLn(s"Filtered(Price > 15) Trades Data:- ${filterTrades.toString}")
	} yield ()

	val env = FileReader.live ++ Console.live ++ Decoder.live ++ TradesFilter.live
	override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = program.provideSomeLayer(env).exitCode
}
