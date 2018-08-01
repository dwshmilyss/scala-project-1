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

  def testRequires(i: Int) = {
    /**
      * Exception in thread "main" java.lang.IllegalArgumentException: requirement failed: i:-1 must be non negative
      */
    require(i > 0, s"i:$i must be non negative")

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

    case class A(var a: String)
    val a1 = A("aa")
    val b1 = A.apply("bb")
    println(a1)
    println(b1)

    var a: Char = '中'

    //    testRequires(-1)


    def seqno(m: Int, n: Int): Int = {
      val s = "the max between m and n is =%d,%d"
      println(s.format(m, n))
      return math.max(m,n)
    }
    def combine(m: Int, n: Int): Int = {
      val s = "com_exp=%d+%d"
      println(s.format(m, n))
      return m + n
    }

    val d = List(1, 2, 3, 4)
//        val e1 = d.par.aggregate(5)(seqno, combine)
    val e = d.aggregate(5)(seqno, combine)
    println("e:" + e)

    val b = List(1, 2, 3, 4)


    val res = aggregate(b)(5)(math.max(_, _), _ + _)
    println(res)

    def x(x1: Int,x2:Int): Int = {
      return math.max(x1,x2)
    }

    def y(y1:String):String = {
      println(s"$y1")
      y1
    }
    println("=============================")
    def test111(a: (Int,Int) => Int)(b: String => String) = {
      a
      b("aaa")
    }
    test111(x)(y)
  }

  def foldLeft[Int](a: List[Int])(z: Int)(op: (Int, Int) => Int): Int = {
    var result = z
    a foreach (x => result = op(result, x))
    result
  }


  def aggregate[Int](a: List[Int])(z: => Int)(seqop: (Int, Int) => Int, combop: (Int, Int) => Int): Int = foldLeft(a)(z)(seqop)



}
