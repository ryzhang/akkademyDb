package com.akkaademy.messages

import akka.actor.{Actor, ActorSystem, Props, Status}
import akka.event.Logging

import scala.collection.mutable

class AkkademyDb extends Actor {
  val map = new mutable.HashMap[String, Object]
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case SetRequest(key, value) => {
      log.info("Received set request - key {} value {}", key, value)
      map.put(key, value)
      sender() ! Status.Success
    }

    case GetRequest(key) => {
      log.info("received GetRequest - key: {}", key)
      val value = map.get(key)
      value match {
        case Some(x) => sender ! x
        case None => sender ! Status.Failure(new KeyNotFoundException(key))
      }
    }

    case o => {
      log.info("receive the unknown message: {}", o)
      Status.Failure(new ClassNotFoundException())
    }
  }

}


object Main extends App {
  val system = ActorSystem("akkademy")
  val helloActor = system.actorOf(Props[AkkademyDb], name = "akkademy-db")

}

