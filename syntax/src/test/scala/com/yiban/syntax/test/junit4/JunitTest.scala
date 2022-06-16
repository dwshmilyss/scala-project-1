package com.yiban.syntax.test.junit4

import org.junit.{After, AfterClass, Assert, Before, BeforeClass, Test}

import scala.collection.Seq

@Test
class JunitTest extends Assert{

  val map = Map("one" -> 1, "two" -> 2, "three" -> 3)
  val list = List(1, 2, 3, 4, 5)
  val emptyList = Nil

  /**
   * 每个测试方法开始前执行一次
   */
  @Before
  def before():Unit = {
    println("before")
  }

  /**
   * 每个测试方法结束后执行一次
   */
  @After
  def after():Unit = {
    println("after")
  }

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

object JunitTest {
  /**
   * 所有测试方法开始前只执行一次
   */
  @BeforeClass
  def setup() : Unit = {
    println("only execute once before all test method");
  }

  /**
   * 所有测试方法结束后只执行一次
   */
  @AfterClass
  def down() : Unit = {
    println("only execute once after all test method");
  }
}
