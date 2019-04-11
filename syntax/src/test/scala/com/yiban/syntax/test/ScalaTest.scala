package com.yiban.syntax.test

import org.scalatest.FunSuite

import scala.collection.Seq

class ScalaTest extends FunSuite{

  val map = Map("one" -> 1, "two" -> 2, "three" -> 3)
  val list = List(1, 2, 3, 4, 5)
  val emptyList = Nil

  test("test1") {
    println("aaa")
  }

}
