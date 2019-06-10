package com.yiban.scala.syntax

import java.io.PrintStream
import java.util.Date

  /**
    * Created by 10000347 on 2016/5/24.
    */
  object TraitTest extends App{
    //实例化对象的时候可以混入特质
    //  val my = new My with ConsoleLogger
    //  my.test("abc")
    //  val my1 = new My with TimestampLogger
    //  my1.test("abc")
    //  val my2 = new My with ShortLogger
    //  my2.test("abcddddddddddddddddddddd")

    //声明了自身类型的特质只能混入自身类型要求的类 例如
    val myException = new MyException with LoggedException
    // 但不能用JFrame 编译报错
//    val jFrame = new JFrame() with  LoggedException

    /**
      * 构造顺序  这和书上的不太一样，书上说实现类被最后构造，但是我这里看到My在超类
      *  Logger被构造
      *  Person被构造
      *  My被构造
      *  ConsoleLogger被构造
      *  TimestampLogger被构造
      *  ShortLogger被构造
      */
//    val my3 = new My with TimestampLogger with ShortLogger
//    my3.test("abcdefghijklmnopkrst")

//    //解决NullPointerException的方案1，但是这样的话 my4就不是My的对象  而是FileLogger的
//    val my4 = new {val fileName = "outer_My.log"} with FileLogger{
//      //这样做会报java.lang.NullPointerException  因为FileLogger构造器在My构造器前执行 那时候fileName未被初始化
////      override val fileName: String = "my.log"
//    }
//    my4.log("aaaa")
//    //解决方案1
//    val my5 = new My with FileLogger{
//
//    }
//    my5.log("bbb")
//    val you = new You with FileLogger{
//      override val fileName: String = "lazy_attr.log"
//    }
//    you.log("lazy")
  }

  /**
    * trait和class的唯一区别就是trait不能有带参的构造器，其他特性和类一模一样
    */
  trait Logger{
    println("Logger被构造")
    def log(data:String) {}
  }

  trait ConsoleLogger extends Logger{
    println("ConsoleLogger被构造")
    override def log(data:String) {println ("data = " + data)}
  }

  //扩展特质，给日志加上时间戳
  trait TimestampLogger extends ConsoleLogger{
    println("TimestampLogger被构造")
    override def log(data:String): Unit = {
      super.log(new Date() + " "+ data)
    }
  }

  //扩展特质，超过15的日志截取
  trait ShortLogger extends ConsoleLogger{
    //特质中可以声明字段，当然也可以声明抽象字段 如果是抽象字段 无需赋初始值 但在实现类中必须给定值
    val maxLength = 15
    println("ShortLogger被构造")
    override def log(data:String): Unit = {
      super.log(if(data.length > maxLength) data.substring(0,maxLength) else data)
    }
  }

  trait FileLogger extends Logger{
    //解决NullPointerException的方案3，使用lazy定义属性，但是lazy在每次使用前都会检查是否已初始化，所以不高效
    val fileName : String
    lazy val out = new PrintStream(fileName)
//    lazy val out = new PrintStream(fileName)
    override def log(msg:String){out.println(msg);out.flush()}
  }

package test{
  abstract class Person extends Logger{
    println("Person被构造")
    override def log(data:String) {}
  }

  //解决NullPointerException的方案2，extends是提前定义块
  class My extends {
    val fileName = "inner_My.log"
  } with Person {
    println("My被构造")
    def test (data:String): Unit ={
      log(data)
    }
  }

  class You extends Person {
    println("You被构造")
    def test (data:String): Unit ={
      log(data)
    }
  }
}

  //自身类型
  trait LoggedException extends Logger{
    this:Exception => def log(){log(getMessage)}
  }

  class MyException extends Exception{

  }
