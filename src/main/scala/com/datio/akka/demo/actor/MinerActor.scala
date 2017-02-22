package com.datio.akka.demo.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.datio.akka.demo.{RequestMaterials, ResponseMaterials}

object MinerActor {
  def props(): Props = Props(classOf[MinerActor])
}

/**
  * This actor is in charge of provide materials for building the Death Star according to the plan
  **/
class MinerActor extends Actor with ActorLogging {

  def receive: Receive = {
    case requestMaterials: RequestMaterials => handleRequest(requestMaterials)
  }

  private def handleRequest(request: RequestMaterials) = {
    log.info(s"${getClass.getName()} Extracting materials ...")
    val materials: List[String] = request.plans.map(s => "package" + s.split("section")(1))
    val response = ResponseMaterials(materials)

    sender ! response
  }
}