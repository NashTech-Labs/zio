package org.knoldus.edu.api

import org.knoldus.edu.domain.{Trip, TripService, api, constants, errors}
import org.knoldus.edu.domain.TripServiceImp.APIResponse
import zhttp.http.Method.{GET, POST, PUT}
import zhttp.http._
import zio.json._
import zio.{RIO, URIO, ZIO}
import org.knoldus.edu.domain.api._
import org.knoldus.edu.domain.errors.DecodeError

object TripApi {
   val RootPath = "trip"

   val apiRoute: HttpApp[TripService,Throwable] = Http.collectZIO
   {

     case GET -> _/ RootPath / "health" => ZIO.succeed(Response.ok)

      case req @ POST -> _ /RootPath/ "create" =>
       httpResponseHandler[TripService,APIResponse[String]](
       for{
           body <- req.body.asString
           trip <- ZIO.fromEither(api.parseTripRequest(body))
           apiResponse <- TripService.createTrip(trip)
         } yield apiResponse ,apiResponse => convertToHttpResponse[String](apiResponse)( m => Response(status = Status.Created,body = Body.fromString(m) ))
       )

     case req @ GET -> _/ RootPath / "retrieve"  if req.url.queryParams.contains("tripId") => httpResponseHandler[TripService,APIResponse[Trip]](
        for{
           tripId <- ZIO.fromOption(req.url.queryParams.get("tripId").flatMap(_.headOption)).orElseFail(DecodeError("invalid query parameter"))
           apiResponse <- TripService.findTrip(tripId)
        } yield apiResponse,apiResponse => convertToHttpResponse[Trip](apiResponse)(trip => Response(status = Status.Ok,body = Body.fromString(trip.toJson)))
     )


     case req@GET -> _ / RootPath / "retrieveAll"  => httpResponseHandler[TripService, APIResponse[List[Trip]]](
       for {
         apiResponse <- TripService.findAllTrip
       } yield apiResponse, apiResponse => convertToHttpResponse[List[Trip]](apiResponse)(trips => Response(status = Status.Ok, body = Body.fromString(trips.toJson)))
     )


     case req @ PUT -> _/ RootPath / "updateTrip"  if req.url.queryParams.contains("tripId") => httpResponseHandler[TripService, APIResponse[Unit]](
       for{
            tripId <- ZIO.fromOption(req.url.queryParams.get("tripId").flatMap(_.headOption)).orElseFail(DecodeError("Invalid query parameter"))
            body <- req.body.asString
            tripTodUpdate <- ZIO.fromEither(api.parseTripRequest(body))
            apiResponse <- TripService.update(tripId,tripTodUpdate)
       } yield apiResponse, apiResponse => convertToHttpResponse[Unit](apiResponse)(_ => Response.ok)
   )


     case req @ GET -> _/ RootPath / "remove" if req.url.queryParams.contains("tripId") => httpResponseHandler[TripService,APIResponse[Unit]](
       for{
          tripId <- ZIO.fromOption(req.url.queryParams.get("tripId").flatMap(_.headOption)).orElseFail(DecodeError("Invalid query parameter"))
          apiResponse <- TripService.delete(tripId)
       } yield apiResponse, apiResponse => convertToHttpResponse[Unit](apiResponse)(_ => Response.ok)
     )
   }


   def convertToHttpResponse[T](result: APIResponse[T])(fn: T => Response):Response={
     result match {
       case Left(error) if(error.code == constants.SERVER_ERROR_CODE) => Response(status = Status.InternalServerError,body = Body.fromString(error.toJson))
       case Left(error) if (error.code == constants.NOT_FOUND_ERROR_CODE) => Response(status = Status.NotFound, body = Body.fromString(error.toJson))
       case Left(error) => Response(status = Status.InternalServerError,body = Body.fromString(error.toJson))
       case Right(v) => fn(v)
     }
   }

   def httpResponseHandler[R,A](rio: RIO[R,A], onSuccess: A => Response) : URIO[R,Response]  = {
       rio.fold(
          {
             case errors.DecodeError(msg) => Response.fromHttpError(HttpError.BadRequest(msg))
             case errors.NotFound(msg) => Response.fromHttpError(HttpError.UnprocessableEntity(msg))
             case e => Response.fromHttpError(HttpError.InternalServerError(e.getMessage))
          },
          onSuccess
       )
   }
}