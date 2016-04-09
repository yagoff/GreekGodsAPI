package org.yagoff.gga

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import org.yagoff.gga.gods.GodsRoutes

object Main extends App with GodsRoutes {
  implicit val system = ActorSystem("gga-system")
  implicit val materializer = ActorMaterializer()

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
