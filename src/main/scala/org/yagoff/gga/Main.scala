package org.yagoff.gga

import java.util.concurrent.atomic.AtomicLong

import scala.collection.mutable

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

case class God(id: Option[Long], name: String, purposes: List[String])

case class IdGenerator() {
  private val id = new AtomicLong
  def next: Long = id.incrementAndGet
}

trait AkkaJsonProtocol extends DefaultJsonProtocol {
  implicit val godFormat = jsonFormat3(God.apply)
}

object Main extends App with AkkaJsonProtocol {
  implicit val system = ActorSystem("gga-system")
  implicit val materializer = ActorMaterializer()

  //database
  var db = mutable.HashSet[God]()
  private val idGenerator = IdGenerator()
  def nextTaskId(): Long = idGenerator.next

  val routes =
    path("") {
      get {
        complete { "Welcome to the Olympus" }
      }
    } ~
    path("gods") {
      get {
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
          val godWithId = god.copy(id = Some(nextTaskId()))
          db += godWithId
            complete {
              godWithId
            }
          }
        }
      }
    }

  // start the server
  val bindingFuture = Http().bindAndHandle(routes, "localhost", 8001)

  // wait for the user to stop the server
  println("Press <enter> to exit.")
  Console.in.read.toChar

  // gracefully shutdown the server
  import system.dispatcher
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
