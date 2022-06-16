package com.yiban.syntax.test.scalatest

import org.scalatest.FlatSpec

/**
 * FlatSpec结构像xUnit一样扁平，简单和熟悉，但测试名称必须以规范样式编写："X should Y"，"A must B" 等等
 */
class FlatSpecTest extends FlatSpec{

  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  it should "produce NoSuchElementException when head is invoked" in {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }
}
