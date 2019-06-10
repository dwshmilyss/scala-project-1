package com.yiban.scala.akka.dev

import akka.actor.Actor

/**
  * Created by 10000347 on 2015/6/19.
  */
class Greeter extends Actor{
  override def receive: Receive = {
    case Greeter.Greet =>
      println("hello efg!!!")
      sender ! Greeter.Done
  }
}

object Greeter {
  case object Greet
  case object Done
}
