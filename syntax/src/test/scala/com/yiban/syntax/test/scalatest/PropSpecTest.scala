package com.yiban.syntax.test.scalatest

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{Matchers, PropSpec}

import scala.collection.immutable._

/**
 * PropSpec非常适合那些想要在财产检查方面专门编写测试的团队; 当选择不同的样式特征作为主要单元测试样式时，也是编写偶尔测试矩阵的好选择
 */
class PropSpecTest extends PropSpec with TableDrivenPropertyChecks with Matchers{
  val examples =
    Table(
      "set",
      BitSet.empty,
      HashSet.empty[Int],
      TreeSet.empty[Int]
    )

  property("an empty Set should have size 0") {
    forAll(examples) { set =>
      set.size should be (0)
    }
  }

  property("invoking head on an empty set should produce NoSuchElementException") {
    forAll(examples) { set =>
      a [NoSuchElementException] should be thrownBy { set.head }
    }
  }
}
