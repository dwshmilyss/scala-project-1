package com.yiban.scala.syntax

import java.io.File

import scala.io.Source
import scala.util.parsing.json._

object ImplicitDemo {

  class SwimmingType {
    def wantLearning(sw: String) = println("兔子要学:" + sw)
  }

  class AminalType

  object Swimming {
    implicit def learningType(s: AminalType) = new SwimmingType
  }

  def main(args: Array[String]): Unit = {
    val rabbit = new AminalType
    import Swimming._
    rabbit.wantLearning("蛙泳")
  }

  /**
    * implicit修饰类的时候不能和case同时使用
    * 当程序类型检查出现问题的时候，Scala编译器会在两个位置上对出现问题的语句尝试使用隐式转换：
    * 第一个位置是 “可以直接将某个类型转换为期望的类型” 的地方
    * 第二个位置是 “可以将调用方法的对象转换为合适类型” 的地方
    *
    * @param sc
    */
  implicit class JsonForStringContext(val sc: StringContext) {
    def json(values: Any*): JSONObject = {
      val keyRE = """^[\s{,]*(\S+):\s*""".r
      val keys = sc.parts map {
        case keyRE(key) => key
        case str => str
      }
      val kvs = keys zip values
      JSONObject(kvs.toMap)
    }
  }

  // 定义隐式类，实现两个类相加
  implicit class Calculator(x: Int) {
    def add(second: Int) = x + second;
  }


  //隐式方法
  implicit def file2RichFile(file: File) = new RichFile(file)
}

class RichFile(val file: File) {
  def read = Source.fromFile(file.getPath).mkString
}


object ImplicitTest {

  //隐式参数
  def calc(amount: Float)(implicit rate: Float) = amount * rate

  /**
    * 使用隐式类or隐式方法的第一步是要在作用域内 所以要导入
    */
  import ImplicitDemo._

  def main(args: Array[String]): Unit = {
    //变量名无所谓
    //    implicit val currentRate = 0.8f
    implicit val rate = 0.8f
    println(calc(50000.0f))

    println("==========================")

    //    val name = "david duan"
    val name = 123
    val book = "scala,second"
    //注意 jsonObj的类型是JSONObject 并不是String
    //由于隐式类中的参数是StringContext类型，所以这里调用的时候要写成json""，这里的方法调用不加括号，是因为
    //任何时候编译器遇到一个如下形式的字符串字面值：id"string" 它都会被转换成一个StringContext实例的call(id)方法
    /*    当编译器遇到"{name:$name,book:$book"}"，它将会被重写成如下表达式：
        new StringContext("{name:",",book:","}").json(name,book)
        隐类则被重写成如下形式
        new JsonHelper(new StringContext("{name:",",book:","}")).json(name,book)*/
    //    val jsonObj = json"""{name:$name,book:$book}"""
    val jsonObj = new StringContext("{name:", ",book:", "}").json(name, book)

    println(jsonObj.isInstanceOf[JSONObject])
    println(jsonObj.isInstanceOf[String])
    println(jsonObj)

    //本来int是没有add方法的，但是由于隐式类的存在
    println(1.add(100))

    /**
      * 通过隐式方法将File转换为RichFile，这样就可以调用RichFile的read方法
      */
    println(new File("E:\\input.txt").read)

  }
}
