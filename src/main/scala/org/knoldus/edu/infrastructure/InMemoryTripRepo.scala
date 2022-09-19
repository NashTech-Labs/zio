package org.knoldus.edu.infrastructure

import org.knoldus.edu.domain.{Trip, TripNotFoundException, TripRepo}
import zio.{IO, Task, ULayer, ZIO, ZLayer}

import scala.collection.mutable.HashMap
import scala.util.Random

class InMemoryTripRepo(db: scala.collection.mutable.HashMap[String, Trip]) extends TripRepo {

  override def select(id: String): IO[TripNotFoundException,Trip] = {
    db.get(id) match {
      case Some(trip) => ZIO.succeed(trip)
      case None => ZIO.fail(TripNotFoundException(s"Trip id ${id} not found"))
    }
  }
  override def selectAll: Task[List[Trip]] =
    ZIO.succeed(db.values.toList)

  override def insert(trip: Trip): Task[String] = ZIO.succeed{
    val tripId = Random.alphanumeric.take(5).foldLeft("")((result, c) => result + c)
    println(tripId)
    db.put(tripId, trip.copy(id = Some(tripId)))
    tripId
  }

  override def update(id: String, trip: Trip): IO[TripNotFoundException,Unit] ={
    db.get(id) match {
      case Some(trip) => {
          db.update(id,trip)
          ZIO.unit
      }
      case None => ZIO.fail(TripNotFoundException(s"Trip id ${id} not found"))
    }

  }


  override def delete(id: String): IO[TripNotFoundException,Unit]  =  {
    db.get(id) match {
      case Some(_) => {db.remove(id)
        ZIO.unit}
      case None => ZIO.fail(TripNotFoundException(s"Trip id ${id} not found"))
    }
  }
     
}

object InMemoryTripRepo{
    lazy val live: ULayer[InMemoryTripRepo] = ZLayer.succeed(new InMemoryTripRepo(HashMap[String, Trip]().empty))
}