package edu.knoldus

import zio.{ZManaged, _}
import java.io.File
import scala.io.{BufferedSource, Source}

object FileReaderService {
	type FileReader = Has[FileReader.Service]

	object FileReader {
		trait Service {
			def getFileData(file: String): ZIO[Any, Throwable, String]
		}

		val live: ULayer[Has[Service]] = ZLayer.succeed {
			new Service {
				override def getFileData(file: String): ZIO[Any, Throwable, String] = {
					def acquire(file: String) = ZIO.effect(Source.fromFile(new File(file)))

					def release(source: BufferedSource) = ZIO.effectTotal(source.close())

					ZManaged.make(acquire(file))(release).use {
						source =>
							ZIO(source.getLines().mkString)
					}
				}
			}
		}

	}

	def getFileData(file: String): ZIO[FileReader, Throwable, String] = ZIO.accessM[FileReader](_.get.getFileData(file))
}
