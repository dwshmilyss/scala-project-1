package com.yiban.scala.syntax

import scala.reflect.ClassTag

/**
  * Created by duanwei on 2017/3/10.
  */
object GenericTest {
  /*
   *  泛型[]，中括号F、S、T都表示运行时参数类型，
   * ClassTag[T]保存了泛型擦除后的原始类型T,提供给被运行时的。
   */
  class Triple[F: ClassTag, S, T](val first: F, val second: S, val third: T)

  def main(args: Array[String]): Unit = {
//    // 运行执行代码：val triple: Triple[String, Int, Double]
//    val triple = new Triple("Spark", 3, 3.1415)
//
//    // 运行执行代码：val bigData: Triple[String, String, Char]
//    val bigData = new Triple[String, String, Char]("Spark", "Hadoop", 'R');
//
//    // getData函数传入泛型为T的运行时List类型参数，返回list.length / 2的整数。
//    def getData[String](list:List[String]) = list(list.length / 2)
//    // List索引从0开始，执行结果：Hadoop
//    println(getData(List("Spark","Hadoop",123)));
//
//    // 获得getData函数引用
//    val f = getData[Int] _
//    // 调用getData函数，执行结果：4
//    println(f(List(1,2,3,4,5,6)));
    testClassTag()
    println(classOf[String])



  }

  /**
   * ClassTag:在运行时指定，在编译时无法确定的
   */
  def testClassTag(): Unit ={
//    def mkArray[T:ClassTag](elems:T*) = Array[T](elems:_*)
    def mkArray1[T:ClassTag](elems:String*) = Array[String](elems:_*)
    def mkArray2[T](elems:String*) = Array[String](elems:_*)
    def mkArray3[ClassTag](elems:String*) = Array[String](elems:_*)
    def mkArray4[str](elems:Char*) = Array[Char](elems:_*)

    /*
     *  执行结果：
     *  42
     *  13
     */
    mkArray1("42","13").foreach(println)

    /*
     * 执行结果：
     * Japan
     * Brazil
     * Germany
     */
    mkArray2("Japan","Brazil","123").foreach(println)
    mkArray3("11","22","123").foreach(println)
    mkArray4('a','b','c').foreach(println)
  }

}
