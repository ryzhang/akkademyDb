package com.akkaademy.messages

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.{FunSpecLike, Matchers}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class ScalaAskExampleTest extends FunSpecLike with Matchers {
  val system = ActorSystem()

  implicit val timeout = Timeout(5 seconds)

  val pongActor = system.actorOf(Props(classOf[ScalaPongActor]))

  describe("Pong actor"){
    it("should response with Pong") {
      val future: Future[Any] = pongActor ? "Ping"
      val result = Await.result(future.mapTo[String], 1 second)
      assert(result == "Pong")

    }

    it("should fail on unknown message") {
      val future = pongActor ? "Hello"
      intercept[Exception] {
        Await.result(future.mapTo[String], 1 second)
      }
    }
  }

  describe("FutureExamples"){
    import scala.concurrent.ExecutionContext.Implicits.global
    it("should print to console"){
      (pongActor ? "Ping").onSuccess({
        case x: String => println("replied with : " + x)
      })
      Thread.sleep(100)
    }
  }

}
