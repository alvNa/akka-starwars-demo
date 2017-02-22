package com.datio.akka.demo


case class RequestBuilding()

case class ResponseBuilding(message: String, status: String = "PENDING", execution: scala.collection.immutable.List[(String, String)])

case class RequestPlans()

case class ResponsePlans(plans: scala.collection.immutable.List[String])

case class RequestMaterials(plans: scala.collection.immutable.List[String])

case class ResponseMaterials(materials: scala.collection.immutable.List[String])

case class RequestWorkerBuilding(plans: scala.collection.immutable.List[String], materials: scala.collection.immutable.List[String])
