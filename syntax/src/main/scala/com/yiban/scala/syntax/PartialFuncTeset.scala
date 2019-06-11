package com.yiban.scala.syntax

/**
  * Created by 10000347 on 2016/6/13.
  */
object PartialFuncTeset {
  val signal: PartialFunction[Int, Int] = {
    case x if x > 1 => 1
    case x if x < -1 => -1
//    case _ => 0
  }

  val composed_signal: PartialFunction[Int,Int] = signal.orElse{
    case 0 => 0
  }

  val signal_compose: Function1[Int, Int] = signal.compose{
    case x => x - 1
  }

  val signal_andThen: Function1[Int, Int] = signal.andThen{
    case x => x - 1
  }


  val test: PartialFunction[Int,Int] = {case a:Int => a+1}

  def main(args: Array[String]) {
    println(signal(-2))
    println(signal(2))
    println(signal.isDefinedAt(1))
//    println(signal(0))

    println(composed_signal(0))
    println(composed_signal(2))
    println(composed_signal(-2))

    println(test(3))
  }
}
