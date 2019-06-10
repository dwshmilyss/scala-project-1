package com.yiban.scala.syntax

import com.yiban.scala.syntax

import scala.beans.BeanProperty

object ClassDemo {

  def main(args: Array[String]) {
    val a = new A("dw", 18)
    a.age = 19

    val counter = new Counter
    counter.setValue(1)
    println(counter.getValue)
    counter.newVal = 1

    val man = new syntax.Person("aa",20)
    val woman = new syntax.Person("bb",18)

    new man.Man("male")
    new woman.Man("female")
  }

  class Name(s: String) {
    private var _value: String = s

    def value: String = _value

    def value_=(newValue: String) = _value = newValue
  }

  class A(val name: String, var age: Int)

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
class Counter{
  //如果成员变量是private 则scala会自动生成private的getter和setter，这时需要手动生成
  private var value = 0
  //用下划线代替默认值时必须指定类型
  var newVal : Int = _
  var str : String = _
  def increment() {value += 1}

  private [this] var priValue = 0

  //获取值时不用写括号
  def getValue = value
  def setValue(a : Int) = value = a

  this.priValue

  @BeanProperty var name : String = _
}

//私有字段 公有的getter和setter
class Person(val name : String,val age :Int){
  outer =>
  class Woman (val gender:String){
    println("gender is "+gender+" name is "+outer.name+" age is "+outer.age)
  }

  class Man (val gender:String){
    println("gender is "+gender+" name is "+name+" age is "+age)
  }

  println(" name is "+name+" age is "+age)
}

//等价于 private[this] name 和 private[this] age
class Student(name : String,age :Int){

}

//私有字段 私有的getter和setter
class Teacher(private val name : String,private val age :Int){

}

//私有字段 私有的getter和setter
class Professor(@BeanProperty val name : String, @BeanProperty val age :Int){

}


class Account {
  private var balance = 0.0
  private[this] val id = 0
  //访问伴生对象的方法时必须用 伴生对象.方法() 才可以
  Account.test()
}

object Account{
  val account = new Account()
  //可以访问伴生类的私有成员 但不能访问伴生类的私有对象成员
  account.balance

  def test() = println("object account")
}