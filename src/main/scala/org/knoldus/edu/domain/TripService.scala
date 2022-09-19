package org.knoldus.edu.domain

import org.knoldus.edu.domain.TripServiceImp.APIResponse
import zio.{Cause, UIO, ZIO, ZLayer}
import zio.macros.accessible

@accessible
trait TripService{
  def findTrip(id: String): UIO[APIResponse[Trip]]
  def createTrip(trip: Trip): UIO[ APIResponse[String]]
  def update(id: String, trip: Trip): UIO[APIResponse[Unit]]
  def delete(id: String): UIO[APIResponse[Unit]]
  def findAllTrip : UIO[APIResponse[List[Trip]]]
}


case class TripServiceImp(tripRepo: TripRepo) extends TripService {

  override def findAllTrip: UIO[APIResponse[List[Trip]]] = {
    tripRepo.selectAll.map(Right(_)).catchAll(handleAndLogException[List[Trip]])
  }
  def findTrip(id: String): UIO[APIResponse[Trip]]= {
    tripRepo.select(id).map(Right(_)).
      catchAll(handleAndLogException[Trip])
  }

  def createTrip(trip: Trip): UIO[APIResponse[String]] = {
    tripRepo.insert(trip).map(Right(_))
      .catchAll(handleAndLogException[String])

  }

  def update(id: String, trip: Trip): UIO[APIResponse[Unit]] = {
    tripRepo.update(id, trip).map(Right(_)).catchAll(handleAndLogException[Unit])
  }

  def delete(id: String): UIO[APIResponse[Unit]] = {
    tripRepo.delete(id).map(Right(_)).catchAll(handleAndLogException[Unit])
  }

  def handleAndLogException[T]: Throwable => UIO[APIResponse[T]] = (ex: Throwable) => ex match {
    case TripNotFoundException(errorMsg) => ZIO.log(errorMsg) *> ZIO.logError(errorMsg).as(Left(ErrorMessage(constants.NOT_FOUND_ERROR_CODE, errorMsg)))
    case t: Throwable => ZIO.logErrorCause(constants.SERVER_ERROR_DESCRIPTION, Cause.fail(t)).as(Left(ErrorMessage(constants.NOT_FOUND_ERROR_CODE, constants.SERVER_ERROR_DESCRIPTION)))
  }
}

object TripServiceImp{
  type APIResponse[R] = Either[ErrorMessage, R]
  lazy val live: ZLayer[TripRepo, Nothing, TripService] = ZLayer.fromFunction(TripServiceImp(_))
}