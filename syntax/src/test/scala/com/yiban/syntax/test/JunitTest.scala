package com.yiban.syntax.test

import org.junit.{Assert, Test}

import scala.collection.Seq

@Test
class JunitTest extends Assert{

  val map = Map("one" -> 1, "two" -> 2, "three" -> 3)
  val list = List(1, 2, 3, 4, 5)
  val emptyList = Nil


  @Test
  def test1 = {
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
    assert(true)
  }


  @Test
  def test2={
    println("aaa")
  }
}
