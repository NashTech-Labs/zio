package org.knoldus.edu.domain

import zio.{IO, Task}

trait TripRepo {
  def selectAll: Task[List[Trip]]

  def select(id: String):  IO[TripNotFoundException,Trip]

  def insert(trip: Trip): Task[String]

  def update(id: String, trip: Trip): IO[TripNotFoundException,Unit]

  def delete(id: String): IO[TripNotFoundException,Unit]
}
