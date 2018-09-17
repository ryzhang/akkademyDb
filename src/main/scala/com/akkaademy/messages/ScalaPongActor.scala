package com.akkaademy.messages

import akka.actor.{Actor, Status}

class ScalaPongActor extends Actor {
  override def receive: Receive = {
    case "Ping" => sender ! "Pong"
    case _ => sender ! Status.Failure(new Exception("Unknown message"))
  }
}
