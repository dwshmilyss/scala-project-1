package com.yiban.scala.synax

/**
  * Created by duanwei on 2017/3/10.
  */
object PredefineTest {
  def main(args: Array[String]):Unit = {
    val c : Char = 97.asInstanceOf[Char]
    "hello".asInstanceOf[String]
    1.asInstanceOf[Long]
    val it: Seq[String] = List("a", "b")
    it.asInstanceOf[List[String]]

    println("hello".isInstanceOf[String])

    classOf[String]
  }
}
