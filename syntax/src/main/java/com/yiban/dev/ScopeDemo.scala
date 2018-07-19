package com.yiban.dev

object ScopeDemo {
  def main(args: Array[String]) {
    val c1 = new Counter
    val c2 = new Counter
    c2.increment()
    println(c1.isLess(c2))
  }


  class Counter {
    /**
      * 当一个成员变量加上private[this]这个修饰符之后，在本类中可以访问这个变量。在本类的方法中不能访问同一类的其它对象的这个字段，也就形象理解为对象私有的。对于这种对象私有变量，scala根本不会生成getter和setter
      * 如果采用这句话 那么下面的other.value会编译报错
      */
    //    private[this] var value = 0
    private var value = 0

    def increment() {
      value += 1
    }

    def isLess(other: Counter): Boolean = {
      value < other.value
    }
  }

}

package scopeA {

  class Class1 {
    private[scopeA] val scopeA_privateField = 1
  }

}
