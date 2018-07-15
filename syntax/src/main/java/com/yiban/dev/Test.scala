package com.yiban.dev

/**
  * Created by duanwei on 2017/3/13.
  */
object Test {

  case class WhereIn[T](vals: T*)

  val wheres = Seq(WhereIn(1, 2, 3, 4), WhereIn("a", "b", "c"))
  for (where <- wheres) {
    where match {
      //@ _* 匹配case类中的可变参数  注意和集合中的可变参数区分开
      case WhereIn(vals@_*) => println()
      case _ => println()
    }
  }

  @throws(classOf[ArithmeticException])
  def test() = {
    val a = "aaa"
    println(a)
  }

  def testRequires(i:Int) = {
    /**
      * Exception in thread "main" java.lang.IllegalArgumentException: requirement failed: i:-1 must be non negative
      */
    require(i>0,s"i:$i must be non negative")
    /**
      * Exception in thread "main" java.lang.AssertionError: assertion failed: i:-1 must be non negative
      */
    //    assert(i>0,s"i:$i must be non negative")
    /**
      * 以上两者都会中断程序 抛出异常 只是抛出的异常类型不一样而已
      */
    println("sssss")
  }

  def main(args: Array[String]): Unit = {
    val map = Map("one" -> 1, "two" -> 2, "three" -> 3)
    val emptyList = Nil
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

    try {
      test
    } catch {
      case ex: ArithmeticException => println(ex.getMessage)
    }

    case class A(var a:String)
    val a1 = A("aa")
    val b1 = A.apply("bb")
    println(a1)
    println(b1)

    var a : Char = '中'

    testRequires(-1)
  }
}
