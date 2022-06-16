package com.yiban.syntax.test.scalatest

import org.scalatest.FunSuite

import java.io.IOException

/**
 * FunSuite可以轻松编写描述性测试名称，自然地编写集中测试，并生成类似规范的输出
 */
class FunSuiteTest extends FunSuite{

  val map = Map("one" -> 1, "two" -> 2, "three" -> 3)
  val list = List(1, 2, 3, 4, 5)
  val emptyList = Nil

  test("test1") {
    println("aaa")
  }

  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

  test("Invoking head on an empty Set should produce NoSuchElementException") {
    assertThrows[NoSuchElementException] { //等价于intercept[NoSuchElementException]
      Set.empty.head
    }
  }

  test("get execption"){
    intercept[IOException]{
      val a = 1/0
    }
  }
}
