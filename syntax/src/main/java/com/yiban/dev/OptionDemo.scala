package com.yiban.dev

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
    def positive1(i:Int):Either[String,Int] = {
      if(i > 0) Right(i) else Left(s"nonpositive number $i")
    }

    def positive2(i:Int):Option[Int] = {
      if (i>0) Some(i) else None
    }

    for {
      i1 <- positive2(5)
      i2 <- positive2(-1 * i1)//这里如果执行为None 是会阻断的 下面的子句不会执行
      i3 <- positive2(10 * i2)
    }yield (i1 + i2 + i3)


    for {
      i1 <- positive1(5).right
      i2 <- positive1(-1 * i1).right //这里如果执行为None 是会阻断的 下面的子句不会执行
      i3 <- positive1(10 * i2).right
    }yield (i1 + i2 + i3)
  }
}
