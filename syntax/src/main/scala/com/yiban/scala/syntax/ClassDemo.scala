package com.yiban.scala.syntax

object ClassDemo {

  class Name(s: String) {
    private var _value: String = s

    def value: String = _value

    def value_=(newValue: String) = _value = newValue
  }

  class A(val name: String, var age: Int)

  def main(args: Array[String]) {
    val a = new A("dw", 18)
    a.age = 19
  }


  /*==========继承的方式===========*/
  /**
    * 父类是样例类（注意：子类不能再是样例类）
    *
    * @param name
    * @param age
    */
  case class Person(name: String, age: Option[Int])

  /**
    * 子类
    *
    * @param name
    * @param age
    * @param title
    * @param manager
    */
  class Emp(name: String, age: Option[Int], val title: String = "unknown", val manager: Option[Emp]) extends Person(name, age) {
    override def toString: String = super.toString
  }


  /*==========组合的方式===========*/


  class List[+T](val head: T, val tail: List[T]) {
    //将函数也用泛型表示
    //因为是协变的，输入的类型必须是T的超类
    def prepend[U >: T](newHead: U): List[U] = new List(newHead, this)

    override def toString() = "" + head
  }


  /**
    * self不是关键字,是this的别名,具有更强的可读性,无代码中的outer =>
    */
  class Self {
    self =>
    val tmp = "Scala"

    def foo = self.tmp + this.tmp //可以用self和this访问自身成员
  }

  trait S1

  class S2 {
    this: S1 =>
  }


  /**
    * 对比Pair和Pair1  可以知道<: 和 <:< 的区别
    *
    * @param first
    * @param second
    * @tparam T
    */
  class Pair[T <: Ordered[T]](val first: T, val second: T) {
    def smaller = if (first < second) first else second
  }

  class Pair1[T](val first: T, val second: T) {
    def smaller(implicit ev: T <:< Ordered[T]) = if (first < second) first else second
  }


  /**
    * 测试 =:=  <%<  <:<
    *  A =:= B means A must be exactly B
       A <:< B means A must be a subtype of B (analogous to the simple type constraint <:)
       A <%< B means A must be viewable as B, possibly via implicit conversion (analogous to the simple type constraint <%)
    *
    */
  import scala.reflect.runtime.universe.typeOf

  type T = Serializable {
    type X
    def foo(): Unit
  }

  object A extends Serializable {
    type X = String;
    def foo() {}
  }

  /**
    * 用 =:=[A, B] 可以证明A,B两个类型是否等价的，B是A的别名的话，它两是等价的。
    * 但A, B仍是两种类型，它们的type对象是不同的。
    */
  typeOf[A.type] <:< typeOf[T]

}
