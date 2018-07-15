package com.yiban.dev

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
    def prepend[U>:T](newHead:U):List[U]=new List(newHead,this)

    override def toString()=""+head
  }

}
