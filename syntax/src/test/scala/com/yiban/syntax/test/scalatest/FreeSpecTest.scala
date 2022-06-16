package com.yiban.syntax.test.scalatest

import org.scalatest.FreeSpec

/**
 * 因为它给出了如何编写规范文本的绝对自由（并且没有指导），FreeSpec对于有BDD经验并且能够就如何构建规范文本达成一致的团队来说是一个很好的选择。
 */
class FreeSpecTest extends FreeSpec{
  "A Set" - {
    "when empty" - {
      "should have size 0" in {
        assert(Set.empty.size == 0)
      }

      "should produce NoSuchElementException when head is invoked" in {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}
