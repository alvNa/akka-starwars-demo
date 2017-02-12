package com.datio.akka.demo

import akka.actor.{ActorSystem, Props}
import akka.event.Logging
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.datio.akka.demo.Constants._
import com.datio.akka.demo.actor.DirectorActor

import scala.concurrent.duration._

/**
  * Main application
  */
object StarWarsApp extends App {

  implicit val timeout = Timeout(5 seconds)
  implicit val system = ActorSystem(MASTER_KEY)
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  val log = Logging(system, getClass)

  log.info(s"${getClass.getName()} Initialising construction")
  val directorActor = system.actorOf(Props[DirectorActor], DIRECTOR_KEY)
  //Actor first call with message
  val future = directorActor ? RequestBuilding()

  future onSuccess {
    case response: ResponseBuilding =>
      log.info(s">>> ---------------------------------")
      log.info(s">>> ${response.message}")
      log.info(s">>> execution : ${response.execution}")
      log.info(s">>> status : ${response.status.toString}")
      log.info(s">>> ---------------------------------")
    case None => log.error("error")
  }

  system.terminate()
}
