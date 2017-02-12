package com.datio.akka.demo.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.datio.akka.demo.{RequestWorkerBuilding, ResponseBuilding}
import com.datio.akka.demo.Status._


object WorkerActor {
  def props(): Props = Props(new WorkerActor())
}

/**
  * Worker
  * */
class WorkerActor extends Actor with ActorLogging{

  def receive: Receive = {
    case d: RequestWorkerBuilding => handleRequest(d)
  }

  private def handleRequest(request:RequestWorkerBuilding){
    log.info(s"${getClass.getName()} Building Death Star ...")

    val message = ResponseBuilding("Construction ready for doing evil ", "FINISHED", request.plans.zip(request.materials))

    sender ! message
  }
}