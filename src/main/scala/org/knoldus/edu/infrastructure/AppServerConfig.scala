package org.knoldus.edu.infrastructure

import zio.ZLayer
import zio.config.magnolia.descriptor
import zio.config.{PropertyTreePath, ReadError, read}
import zio.config.typesafe.TypesafeConfigSource

case class AppServerConfig(host: String, port: Int)

object AppServerConfig {
  lazy val live: ZLayer[Any, ReadError[String], AppServerConfig] =
    ZLayer {
      read {
        descriptor[AppServerConfig].from(
          TypesafeConfigSource.fromResourcePath
            .at(PropertyTreePath.$("AppServerConfig"))
        )
      }
    }
}