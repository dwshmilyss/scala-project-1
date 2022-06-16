package com.yiban.syntax.test.scalatest

import org.scalatest.FunSpec

/**
 * FunSpec嵌套和温和的结构化文本指南（带describe和it）为编写规范式测试提供了极好的通用选择。
 */
class FunSpecTest extends FunSpec{
  describe("A Set") {
    describe("when empty") {
      it("should have size 0") {
        assert(Set.empty.size == 0)
      }

      it("should produce NoSuchElementException when head is invoked") {
        assertThrows[NoSuchElementException] {
          //intercept[NoSuchElementException] {  旧版本使用，现在仍可用
          Set.empty.head
        }
      }
    }
  }
}
