package org.knoldus.edu

import org.knoldus.edu.api.TripApi
import org.knoldus.edu.domain.TripServiceImp
import org.knoldus.edu.infrastructure.{AppServerConfig, InMemoryTripRepo}
import zhttp.http.Http
import zhttp.service.Server
import zio.Console.{printLine, readLine}
import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}


object TripApp extends ZIOAppDefault{

  val startAppServer: ZIO[Any, Exception, Unit] = for{
    appConfig <- ZIO.service[AppServerConfig].provide(AppServerConfig.live)
    f <- Server.start(appConfig.port,TripApi.apiRoute.withAccessControlAllowOrigin("o") <> Http.notFound)
      .provide(TripServiceImp.live,InMemoryTripRepo.live)
      .forkDaemon
    _ <- printLine(s"Server Started on port ${appConfig.port} Press Any Key to stop server") *> readLine.catchAll( ex => printLine(s"There was an error server can not start !!! ${ex.getMessage}")) *> f.interrupt
  } yield ()

  override def run: ZIO[Any with ZIOAppArgs with Scope , Any, Any] = startAppServer
}
