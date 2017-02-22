package com.datio.akka.demo.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.datio.akka.demo.Constants._
import com.datio.akka.demo._

object DirectorActor {
  def props(): Props = Props(classOf[DirectorActor])
}

/**
  * This actor manages others actors for getting plans and materials
  * before ordering the building of the Death Star.
  */
class DirectorActor extends Actor with ActorLogging {

  val designerActor = context.child(DESIGNER_KEY)
    .getOrElse(context.actorOf(Props(classOf[DesignerActor]), DESIGNER_KEY))
  val minerActor = context.child(MINER_KEY)
    .getOrElse(context.actorOf(Props(classOf[MinerActor]), MINER_KEY))
  val workerActor = context.child(WORKER_KEY)
    .getOrElse(context.actorOf(Props(classOf[WorkerActor]), WORKER_KEY))

  private var originalSender: Option[ActorRef] = None
  private var plans: List[String] = List.empty

  def receive: Receive = {
    case requestBuilding: RequestBuilding => handleRequestBuilding(requestBuilding)
    case responsePlans: ResponsePlans => handleResponsePlans(responsePlans)
    case responseMaterials: ResponseMaterials => handleResponseMaterials(responseMaterials)
    case responseBuilding: ResponseBuilding => handleResponseBuilding(responseBuilding)
  }

  private def handleRequestBuilding(requestBuilding: RequestBuilding) = {
    log.info(s"${getClass.getName()} Orchestrating building ...")
    originalSender = Some(sender)
    designerActor ! requestBuilding
  }

  private def handleResponsePlans(responsePlans: ResponsePlans) = {
    log.info(s"${getClass.getName()} Receiving plans...")
    plans = responsePlans.plans
    minerActor ! RequestMaterials(plans)
  }

  private def handleResponseMaterials(responseMaterials: ResponseMaterials) = {
    log.info(s"${getClass.getName()} Receiving materials...")
    workerActor ! RequestWorkerBuilding(plans, responseMaterials.materials)
  }

  private def handleResponseBuilding(responseBuilding: ResponseBuilding) = {
    log.info(s"${getClass.getName()} Receiving building...")
    originalSender.get ! responseBuilding
  }
}