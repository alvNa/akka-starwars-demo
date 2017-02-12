package com.datio.akka.demo.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.datio.akka.demo.{A, B}



object BActor {
  def props(): Props = Props(new BActor())
}

/**
  * Designer
  */
class BActor extends Actor with ActorLogging{

  def receive: Receive = {
    case _: A => handleRequest()
  }


  private def handleRequest(){
    log.info(s"${getClass.getName()} Designing plans ...")
    val response = B(List[String]("sectionA","sectionB","sectionC"))

    sender ! response
  }
}