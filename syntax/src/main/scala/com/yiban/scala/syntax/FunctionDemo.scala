package com.yiban.scala.syntax

object FunctionDemo {

  def main(args: Array[String]): Unit = {
//    testAndThen()
    testSeniorFunction()
  }

  /**
    * 测试andThen
    * 和另一个Function1实例组合成一个新的Function1实例，
    * 当前这个方法先执行，执行完的结果作为另一个方法的入参
    */
  def testAndThen(): Unit = {
    /**
      * Function1
      * 带一个参数的方法，声明时，它需要两个泛型参数，
      * 第一个是传入的数据类型，第二个表示返回的数据类型，
      * Function1是 trait ，它有一个apply方法，用来对输入参数进行处理了
      * 使用Function1，必须实现apply接口
      */
    val funs = new Function1[Int, Int] {
      override def apply(x: Int): Int = {
        println("第一步 = " + x)
        x + 1
      }
    }

    val succ = (x: Int) => {
      println("第二步 = " + x)
      x + 3
    }

    /**
      * andThen : 先执行succ 再执行funs
      */
    //    println(succ.andThen(funs).apply(5))
    /**
      * compose : 和andThen刚好相反 先执行funs 再执行succ
      */
    println(succ.compose(funs).apply(5))
  }

  /**
    * Function2
    * 带两个参数的方法，它的声明需要三个泛型参数，前两个是入参类型
    * 第三个是返回数据类型，同Function1一样，也要实现apply方法
    */
  def testCurried(): Unit = {
    val funs = new Function2[Int, Int, Int] {
      override def apply(v1: Int, v2: Int): Int = {
        v1 + v2
      }
    }
    //为当前方法创建一个柯里化的版本
    val curryfuns = funs.curried
    println(funs.apply(1,2))
    println(curryfuns(1)(2))
  }

  /**
    * 为当前方法创建一个tupled（元组）版本
    * 即参数可以传元组
    */
  def testTupled(): Unit ={
    val funs = new Function2[Int, Int, Int] {
      override def apply(v1: Int, v2: Int): Int = {
        v1 + v2
      }
    }
    val tupledfuns = funs.tupled
    println(tupledfuns((1,2)))
  }

  /**
    * 测试高阶函数（参数和返回值都是函数的情况）
    */
  def testSeniorFunction(): Unit ={
    def func(name1:String,name2:String,f:(Int,Int) => String) : (String,Int) => Int = {
      val temp = f.curried
      val res = temp(1)(2)
      (a:String,b:Int) => {
        a.size + b + res.size
      }
    }

    val value = func("name1","name2",(a:Int,b:Int) => {
      (a+b).toString
    })("aaa",10)

    println(value)
  }


}

/**
  * 偏函数
  */
object PartialFunctionDemo {
  def main(args: Array[String]) {

    /**
      * 因为map接收的是一个普通的匿名函数 所以要把所有的数据都case到
      */
    val a = List(1, 3, 5, "seven") map {
      case i: Int => i + 1
      case str: String => str
    }

    /**
      * 因为collect接受的是一个偏函数 所以无需对string 进行case
      */
    val b = List(1, 3, 5, "seven") collect {
      case i: Int => i + 1
    }
  }
}

/**
  * Created by wenbronk on 2017/9/12.
  * 测试参数是函数的场景
  */
object TestParamsAsFunction {

  /**
    * 传名参数
    * addByName(2, 2 + 2)
    * ->2 + (2 + 2)
    * ->2 + 4
    * ->6
    *
    * 传值参数
    * addByValue(2, 2 + 2)
    * ->addByValue(2, 4)
    * ->2 + 4
    * ->6
    */
  object Add {
    def addByName(a: Int, b: => Int) = a + b

    def addByValue(a: Int, b: Int) = a + b
  }


  def main(args: Array[String]): Unit = {
    delayTime({
      println("2222");
      time(1)
    })

    /**
      *
      * => Unit 是传名函数, 只传入了一个表达式, 在调用时才会去执行
      * 所以这里会先输出 "执行开始"
      * @param t
      */
    def delayTime(t: => Long): Unit = {
      println("执行开始")
      println(t)
      println("执行结束")
    }

    delayTime2(() => {
      println("333");
      time(2)
    })
  }

  /**
    * () => 是传值函数, 传入的计算后的值, 使用 code() 调用
    * 因为先要计算（即计算调用该函数的表达式，也就是delayTime的参数{println("2222");time}） 所以这里会先输出 "333"。因为这个字符串是固定值 可以预先计算好
    * 然后在输出 "执行..."
    *
    * @param t
    */
  def delayTime2(t: () => Long): Unit = {
    println("执行...")
    println(t())
    println("结束..")
  }

  def time(param: Long) = {
    println("获取时间内, 单位为纳秒 : " + param)
    System.nanoTime()
  }

}


