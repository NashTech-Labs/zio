package org.knoldus.edu.domain

import enumeratum.{Enum, EnumEntry}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}
import zio.json._
import java.time.LocalDate

sealed abstract class Vehicle extends EnumEntry

case class TripNotFoundException(message: String) extends Exception(message)

case class Trip(id: Option[String] = None, mode: String,price: Double,completed: Boolean,distance: Option[Double],endDate: Option[LocalDate])

final case class ErrorMessage(code: String, description: String)

object Vehicle extends Enum[Vehicle] {
  case object Bike extends Vehicle
  case object  Car extends Vehicle
  case object Taxi extends  Vehicle
  val values: IndexedSeq[Vehicle] =findValues
}

object errors {
  final  case class DecodeError(msg: String) extends Error
  final case class InternalServerError(msg: String) extends Error
  final case class NotFound(msg: String) extends Error

}

object api{
  import errors.DecodeError
  val EMPTY_STRING = ""
  implicit val decoderVehicle: JsonDecoder[Vehicle] = DeriveJsonDecoder.gen[Vehicle]
  implicit val encodeVehicle: JsonEncoder[Vehicle] = DeriveJsonEncoder.gen[Vehicle]
  implicit val decoderTrip: JsonDecoder[Trip] = DeriveJsonDecoder.gen[Trip]
  implicit val encodeTrip: JsonEncoder[Trip] = DeriveJsonEncoder.gen[Trip]
  implicit val errorMessageEncoder: JsonEncoder[ErrorMessage] = DeriveJsonEncoder.gen[ErrorMessage]
  def parseTripRequest(msg: String) : Either[DecodeError,Trip] = msg.fromJson[Trip].left.map(e => DecodeError(e))
}



object constants{
  val SERVER_ERROR_DESCRIPTION = "Server Error"
  val SERVER_ERROR_CODE = "500"
  val NOT_FOUND_ERROR_CODE = "404"
}