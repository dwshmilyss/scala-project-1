package com.yiban.scala.akka.dev

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}


/**
  * Created by 10000347 on 2015/6/17.
  */
class HelloActor extends Actor with ActorLogging{

  val system = ActorSystem("HelloSystem")
  override def preStart(): Unit = {
    val greeter = system.actorOf(Props[Greeter],name = "greeter")
    greeter ! Greeter.Greet
  }

  override def receive: Receive = {
//    case "hello" => println("world");log.info("world")
//    case _ => println("unknown");log.info("unknown")
    case Greeter.Done => system.stop(self)
  }
}

//object test {
//  def main(args: Array[String]): Unit = {
//    val system = ActorSystem("HelloSystem")
//    // default Actor constructor
//    val helloActor = system.actorOf(Props[HelloActor],name = "helloActor")
//    helloActor ! "hello"
//  }
//}
