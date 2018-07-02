package com.yiban.dev

import scala.util.parsing.json._

object ImplicitDemo {

  /**
    * implicit修饰类的时候不能和case同时使用
    * @param sc
    */
  implicit class JsonForStringContext(val sc:StringContext){
    def json(values:Any*):JSONObject = {
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
  implicit class Calculator(x:Int){
    def add(second:Int) = x + second;
  }
}


object ImplicitTest{
  def calc(amount: Float)(implicit rate: Float) = amount * rate

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
    val jsonObj = new StringContext("{name:",",book:","}").json(name,book)

    println(jsonObj.isInstanceOf[JSONObject])
    println(jsonObj.isInstanceOf[String])
    println(jsonObj)


    println(1.add(100));
  }
}
