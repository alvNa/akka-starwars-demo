package com.datio.akka.demo.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.datio.akka.demo.{RequestWorkerBuilding, ResponseBuilding}

object WorkerActor {
  def props(): Props = Props(classOf[WorkerActor])
}

/**
  * This actor is in charge of building the Death Star assembling the materials according to the plan
  **/
class WorkerActor extends Actor with ActorLogging {

  def receive: Receive = {
    case requestWorkerBuilding: RequestWorkerBuilding => handleRequest(requestWorkerBuilding)
  }

  private def handleRequest(request: RequestWorkerBuilding) = {
    log.info(s"${getClass.getName()} Building Death Star ...")

    val message = ResponseBuilding("Construction ready for doing evil ", "FINISHED", request.plans.zip(request.materials))

    sender ! message
  }
}