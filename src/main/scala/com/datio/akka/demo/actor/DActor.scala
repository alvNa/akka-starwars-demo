package com.datio.akka.demo.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.datio.akka.demo.{D, E}
import com.datio.akka.demo.Status._


object DActor {
  def props(): Props = Props(new DActor())
}

/**
  * Worker
  * */
class DActor extends Actor with ActorLogging{

  def receive: Receive = {
    case d: D => handleRequest(d)
  }

  private def handleRequest(request:D){
    log.info(s"${getClass.getName()} Building Death Star ...")

    val message = E("Construction ready for doing evil ", "FINISHED", request.plans.zip(request.materials))

    sender ! message
  }
}