package com.yiban.syntax.test.scalatest

import org.scalatest.WordSpec

/**
 * WordSpec对于如何编写文本的要求非常规范，因此非常适合希望在其规范文本上强制执行高度管理的团队。
 */
class WordSpecTest extends WordSpec{
  "A Set" when {
    "empty" should {
      "have size 0" in {
        assert(Set.empty.size == 0)
      }

      "produce NoSuchElementException when head is invoked" in {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}
