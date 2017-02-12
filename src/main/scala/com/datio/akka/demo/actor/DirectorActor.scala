package com.datio.akka.demo.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.datio.akka.demo.Constants._
import com.datio.akka.demo.{RequestBuilding, ResponsePlans, ResponseMaterials, RequestWorkerBuilding, ResponseBuilding}

object DirectorActor {
  def props(): Props = Props(new DirectorActor())
}

/**
  *  This actor manages others actors for getting plans and materials
  *  before ordering the building of the Death Star.
  */
class DirectorActor extends Actor with ActorLogging{

  val bActor = context.child(DESIGNER_KEY).getOrElse(context.actorOf(Props(classOf[DesignerActor]), DESIGNER_KEY))
  val cActor = context.child(MINER_KEY).getOrElse(context.actorOf(Props(classOf[MinerActor]), MINER_KEY))
  val dActor = context.child(WORKER_KEY).getOrElse(context.actorOf(Props(classOf[WorkerActor]), WORKER_KEY))


  private var originalSender:Option[ActorRef] = None
  private var plans : List[String] = List.empty

  def receive: Receive = {
    case a: RequestBuilding => handleRequestBuilding(a)
    case b: ResponsePlans => handleResponsePlans(b)
    case c: ResponseMaterials => handleResponseMaterials(c)
    case e: ResponseBuilding => handleResponseBuilding(e)
  }

  private def handleRequestBuilding(requestBuilding:RequestBuilding) {
    log.info(s"${getClass.getName()} Orchestrating building ...")
    originalSender = Some(sender)
    bActor ! requestBuilding
  }

  private def handleResponsePlans(responsePlans:ResponsePlans)={
    log.info(s"${getClass.getName()} Receiving plans...")
    plans = responsePlans.plans
    cActor ! responsePlans
  }

  private def handleResponseMaterials(responseMaterials:ResponseMaterials)={
    log.info(s"${getClass.getName()} Receiving materials...")
    dActor ! RequestWorkerBuilding(plans, responseMaterials.materials)
  }

  private def handleResponseBuilding(responseBuilding : ResponseBuilding){
    log.info(s"${getClass.getName()} Receiving building...")
    originalSender.get ! responseBuilding
  }

}

