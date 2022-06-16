package com.yiban.syntax.test.scalatest

import org.scalatest.refspec.RefSpec

/**
 * RefSpec允许您将测试定义为方法，与将测试表示为函数的样式类相比，每个测试保存一个函数文字。
 * 更少的函数文字转换为更快的编译时间和更少的生成的类文件，这可以帮助最小化构建时间。
 * 因此，Spec在构建时间受到关注的大型项目中以及通过静态代码生成器以编程方式生成大量测试时，使用可能是一个不错的选择
 */
class RefSpecTest extends RefSpec{
  object `A Set` {
    object `when empty` {
      def `should have size 0` {
        assert(Set.empty.size == 0)
      }

      def `should produce NoSuchElementException when head is invoked` {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}
