package com.yiban.scala.syntax

object FunctionDemo {

}

/**
  * 偏函数
  */
object PartialFunctionDemo {
  def main(args: Array[String]) {

    /**
      * 因为map接收的是一个普通的匿名函数 所以要把所有的数据都case到
      */
    val a = List(1, 3, 5, "seven") map {
      case i: Int => i + 1
      case str: String => str
    }

    /**
      * 因为collect接受的是一个偏函数 所以无需对string 进行case
      */
    val b = List(1, 3, 5, "seven") collect {
      case i: Int => i + 1
    }
  }
}
