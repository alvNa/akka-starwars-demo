package com.datio.akka.demo

import com.datio.akka.demo.Status._

//Request building
case class A()

//Response plans
case class B(plans: scala.collection.immutable.List[String])

//Response materials
case class C(materials: scala.collection.immutable.List[String])

//Request worker building
case class D(plans: scala.collection.immutable.List[String], materials: scala.collection.immutable.List[String])

//Response building
case class E(message:String, status: String = "PENDING", execution: scala.collection.immutable.List[(String,String)])