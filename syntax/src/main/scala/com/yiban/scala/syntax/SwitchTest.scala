package com.yiban.scala.syntax

import scala.collection.Seq

/**
  * Created by 10000347 on 2016/5/31.
  */
object SwitchTest extends App{
  var ch : Char = _
  var sign : Int = _
  ch = 'a'

  ch match {
    case '+' => sign = 1
    case '-' => sign = -1
    case _ if Character.isDigit(ch) => sign = Character.digit(ch,10)
    case _ => sign = 0
  }

  println(sign)


  /**
    * 样例类
    * 1、构造时不需要new
    * 2、自带toString,equals,hashCode,copy方法
    */
  abstract class Amount
  //默认参数都是val的
  case class Dollar(value:Double) extends Amount
  case class Currency(value:Double,unit:String) extends Amount
  case object Nothing extends Amount

//  val amt : Amount = Dollar(2.0)
  val amt : Amount = Currency(2.0,"dw")
//  val amt : Amount = Nothing

  amt match {
    case Dollar(v) => println("$"+v)
//    case Currency(_,u) => println("oh nose ,i got "+u)
//    case Currency(v,u) => println("oh "+u+" ,i got "+v)
    //中置表示法 等同于Currency(v,u) 例如List对象要么是Nil 要么是样例类::，定义：case class ::[E](head:E,tail:List[E])extends List[E]
      //因此 lst match {case h :: t => ...} 等同于 case ::(h,t)，将调用::.unapply(lst)
    case v Currency u => println("oh "+u+" ,i got "+v)
    case Nothing => println("nothing")
  }


  /**
    * 密封类：在编译期所有的子类都是可知的，因此编译器可以检查模式语句的完整性
    * 用sealed模拟枚举
    */
  sealed abstract class Color
  case object Red extends Color
  case object Yellow extends Color
  case object Green extends Color

  val color : Color = Red
  color match {
    case Red => println("stop")
    case Green => println("go")
    //如果少些一个  编译时会报错  但是不影响运行
    case Yellow => println("wait")
  }

  /**
    * 偏函数：被包在{}内的一组case语句是一个偏函数
    * PartialFunction[A,B] A是参数类型  B是返回值类型
    * 该类有2个方法 ：
    *  apply 从匹配到的模式计算函数值
    *  isDifinedAt 在输入至少匹配到其中一个时返回true
    *
    */
  val f : PartialFunction[Char,Int] = {case '+' => 1;case '-' => -1}
  f('-') // f.apply('-') 返回-1
  f.isDefinedAt('0') //false
//  f('0') //抛出MatchError

  "-a+b".collect{case '+' => 1;case '-' => -1}.foreach(println)


  val g1 = (x:Double) => if (x >= 0) Some(scala.math.sqrt(x)) else None

  val g2 = (x:Double) => if (x != 1) Some(1/(x-1)) else None

  def compose(f:(Double) => Option[Double],g:(Double) => Option[Double]) : (Double) => Option[Double] = {
    def com (x:Double) : Option[Double] = {
      if (f(x) == None) return g(x);else return f(x)
    }
    com
  }
  println(compose(g1,g2)(2))

  val emptyList = Nil
  val map = Map("one" -> 1, "two" -> 2, "three" -> 3)
  val list = List(1, 2, 3, 4, 5)

  def windows[T](seq: Seq[T]): String = seq match {
    //匹配集合中不确定的元素个数时用 _* 表示一个或多个  注意和case类中的可变参数区分开
    case Seq(head1, head2, _*) =>
      s"($head1,$head2), " + windows(seq.tail)
    case Seq(head, _*) =>
      s"($head,_), " + windows(seq.tail)
    case Nil => "Nil"
  }

  for (seq <- Seq(list, emptyList, map.toSeq)) {
    println(windows(seq))
  }
}
