package org.yagoff.gga.gods

import akka.http.scaladsl.server.Directives._
import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes

import scala.util.{Failure, Success}

trait AkkaJsonProtocol extends DefaultJsonProtocol {
  implicit val godFormat = jsonFormat3(God.apply)
}

trait GodsRoutes extends AkkaJsonProtocol {

  lazy val godsController = new GodsController()

  val routes =
    path("gods") {
      get {
        val db = godsController.getDB
        if (db.isEmpty) {
          complete {
            "There is no gods on the system!"
          }
        } else {
          complete {
            db.toList
          }
        }
      } ~
      post {
        (entity(as[God])) { god => {
            val godWithId = godsController.addGod(god)
            complete(StatusCodes.Created, godWithId)
          }
        }
      }
    } ~ {
      path("gods" / LongNumber) {
        id => {
          get {
            onComplete(godsController.getGodById(id)) {
              case Success(value) => complete(value)
              case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred: ${ex.getMessage}")
            }
          } ~
          put {
            (entity(as[God])) { god => {
                onComplete(godsController.update(id, god)) {
                  case Success(value) => complete(value)
                  case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred: ${ex.getMessage}")
                }
              }
            }
          } ~
          delete {
            godsController.delete(id)
            complete(StatusCodes.NoContent)
          }
        }
      }
    }
}
