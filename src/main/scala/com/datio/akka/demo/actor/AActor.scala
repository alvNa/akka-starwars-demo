package com.datio.akka.demo.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.datio.akka.demo.Constants._
import com.datio.akka.demo.{A, B, C, D, E}

object AActor {
  def props(): Props = Props(new AActor())
}

/**
  * Director
  *  This actor manages others actors for getting plans and materials
  *  before ordering the building of the Death Star.
  */
class AActor extends Actor with ActorLogging{

  val bActor = context.child(DESIGNER_KEY).getOrElse(context.actorOf(Props(classOf[BActor]), DESIGNER_KEY))
  val cActor = context.child(MINER_KEY).getOrElse(context.actorOf(Props(classOf[CActor]), MINER_KEY))
  val dActor = context.child(WORKER_KEY).getOrElse(context.actorOf(Props(classOf[DActor]), WORKER_KEY))


  private var originalSender:Option[ActorRef] = None
  private var plans : List[String] = List.empty

  def receive: Receive = {
    case a: A => handleRequestBuilding(a)
    case b: B => handleResponsePlans(b)
    case c: C => handleResponseMaterials(c)
    case e: E => handleResponseBuilding(e)
  }

  private def handleRequestBuilding(requestBuilding:A) {
    log.info(s"${getClass.getName()} Orchestrating building ...")
    originalSender = Some(sender)
    bActor ! requestBuilding
  }

  private def handleResponsePlans(responsePlans:B)={
    log.info(s"${getClass.getName()} Receiving plans...")
    plans = responsePlans.plans
    cActor ! responsePlans
  }

  private def handleResponseMaterials(responseMaterials:C)={
    log.info(s"${getClass.getName()} Receiving materials...")
    dActor ! D(plans, responseMaterials.materials)
  }

  private def handleResponseBuilding(responseBuilding : E){
    log.info(s"${getClass.getName()} Receiving building...")
    originalSender.get ! responseBuilding
  }

}

