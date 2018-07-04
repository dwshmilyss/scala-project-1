package com.yiban.dev

import scala.util.Try

object OptionDemo {
  def main(args: Array[String]) {

    /**
      * Option用法
      */
    val results: Seq[Option[Int]] = Vector(Some(10), None, Some(20))
    val results1 = for {
      Some(i) <- results
    } yield (2 * i)

    // 等价于
    val results12 = for {
      Some(i) <- results withFilter {
        case Some(i) => true
        case None => false
      }
    } yield (2 * i)

    // 等价于
    val results13 = results withFilter {
      case Some(i) => true
      case None => false
    } map {
      case Some(i) => 2 * i
    }


    /**
      * Either用法 注意2个positive对比
      */
    def positive1(i: Int): Either[String, Int] = {
      if (i > 0) Right(i) else Left(s"nonpositive number $i")
    }

    def positive2(i: Int): Option[Int] = {
      if (i > 0) Some(i) else None
    }

    for {
      i1 <- positive2(5)
      i2 <- positive2(-1 * i1) //这里如果执行为None 是会阻断的 下面的子句不会执行
      i3 <- positive2(10 * i2)
    } yield (i1 + i2 + i3)


    for {
      i1 <- positive1(5).right
      i2 <- positive1(-1 * i1).right //这里如果执行为None 是会阻断的 下面的子句不会执行
      i3 <- positive1(10 * i2).right
    } yield (i1 + i2 + i3)

    /**
      * 有时候可以用Either代替Exception
      */
    def addInt(s1:String,s2:String):Either[NumberFormatException,Int] = {
      try {
        Right(s1.toInt + s2.toInt)
      } catch {
        case ex : NumberFormatException => Left(ex)
      }
    }
    println(addInt("1","2"))
    println(addInt("1","x"))
    println(addInt("x","2"))

    /**
      * Try类型和Either类似 Success返回正确的值 Failure返回错误的值
      */
    def positive3(i: Int): Try[Int] = Try {
      assert(i > 0,s"no positive number : $i")
      i
    }

    val res1 = for {
      i1 <- positive3(5)
      i2 <- positive3(-1 * i1) //这里如果执行为None 是会阻断的 下面的子句不会执行
      i3 <- positive3(10 * i2)
    } yield (i1 + i2 + i3)

    /**
      * 利用第三方库实现positive()
      */
    import scalaz._,Scalaz._
    import Validation.FlatMap._
    //这里的Validation 其实和Either还有Try的功能都一样 都会在第一个错误出现时阻断后续执行
    def positive4(i: Int): Validation[List[String],Int] = {
      if(i > 0) Success(i)
      else Failure(List(s"no positive number : $i"))
    }



    val res2 = for {
      i1 <- positive4(5)
      i2 <- positive4(-1 * i1) //这里如果执行为None 是会阻断的 下面的子句不会执行
      i3 <- positive4(10 * i2)
      i4 <- positive4(-2 * i3)
    } yield (i1 + i2 + i3 + i4)

    println(res2)

    println(positive4(5) +++ positive4(-10) +++ positive4(25) +++ positive4(-30))

  }
}
