package org.yagoff.gga.gods

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives
import de.heikoseeberger.akkahttpcirce.CirceSupport

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

trait GodsRoutes {

  import CirceSupport._
  import Directives._
  import io.circe.generic.auto._

  lazy val godsController = new GodsController

  val routes =
    path("gods") {
//      get {
//        val db = godsController.getDB
//        if (db.isEmpty) {
//          complete {
//            "There is no gods on the system!"
//          }
//        } else {
//          complete {
//            db.toList
//          }
//        }
//      } ~
      post {
        (entity(as[God])) { god => {
            val godCreated = godsController.addGod(god)
            complete(godCreated)
          }
        }
      }
    }
}
