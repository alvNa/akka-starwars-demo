package com.datio.akka.demo

object Constants {
  val MASTER_KEY = "Emperor"
  val DIRECTOR_KEY = "Director"
  val DESIGNER_KEY = "Designer"
  val MINER_KEY = "Miner"
  val WORKER_KEY = "Worker"

  val DEATH_STAR = "DEATH STAR"
}

object Status extends Enumeration with Serializable {
  type Status = Value
  val PENDING, IN_PROGRESS, FINISHED = Value
}