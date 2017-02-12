package com.datio.akka.demo.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.datio.akka.demo.{RequestBuilding, ResponsePlans}



object DesignerActor {
  def props(): Props = Props(new DesignerActor())
}

/**
  *  This actor is in charge of design the plans for building the Death Star.
  */
class DesignerActor extends Actor with ActorLogging{

  def receive: Receive = {
    case _: RequestBuilding => handleRequest()
  }


  private def handleRequest(){
    log.info(s"${getClass.getName()} Designing plans ...")
    val response = ResponsePlans(List[String]("sectionA","sectionB","sectionC"))

    sender ! response
  }
}