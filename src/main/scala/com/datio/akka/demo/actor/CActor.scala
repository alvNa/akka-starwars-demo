package com.datio.akka.demo.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.datio.akka.demo.{B, C}

object CActor {
  def props(): Props = Props(new CActor())
}

/**
  * Minner
  * */
class CActor extends Actor with ActorLogging{

  def receive: Receive = {
    case b: B => handleRequest(b)
  }

  private def handleRequest(request:B){
    log.info(s"${getClass.getName()} Extracting materials ...")
    val materials:List[String] = request.plans.map(s =>"pack" + s.split("section")(1))
    val response = C(materials)

    sender ! response
  }
}