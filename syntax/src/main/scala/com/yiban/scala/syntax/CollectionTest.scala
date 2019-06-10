package com.yiban.scala.syntax

import scala.collection.mutable.ArrayBuffer


/**
  * Created by 10000347 on 2016/6/22.
  */
object CollectionTest {
  def main(args: Array[String]) {
//    import scalaj.collection.Imports._
//    val javaList = new java.util.ArrayList[Int]
//    javaList.add(1)
//    javaList.add(2)
//    javaList.add(3)
//
//    val scalaList = javaList.asScala
//    for (i <- scalaList) println(i)
//    for (i <- javaList) println(i)
    s2j()


  }

  def j2s():Unit = {
    import scala.collection.JavaConversions
    import scala.collection.JavaConverters._
    val javaList = new java.util.ArrayList[Int]
    javaList.add(1)
    javaList.add(2)
    javaList.add(3)

    val scalaList = javaList.asScala
    for (i <- scalaList) println(i)

    val scalaList1 = JavaConversions.asScalaBuffer(javaList)
    for (i <- scalaList1) println(i)


    val javaSet= new java.util.HashSet[Int]
    javaSet.add(1)
    javaSet.add(2)
    javaSet.add(3)

    val scalaSet = javaSet.asScala
    for (i <- scalaSet) println(i)

    val scalaSet1 = JavaConversions.asScalaSet(javaSet)
    for (i <- scalaSet1) println(i)

    val javaMap= new java.util.HashMap[String,Int]
    javaMap.put("a",1)
    javaMap.put("b",2)
    javaMap.put("c",3)

    val scalaMap = javaMap.asScala
    for ((k,v) <- scalaMap) println(k+" "+v)

    val scalaMap1 = JavaConversions.mapAsScalaMap(javaMap)
    for ((k,v) <- scalaMap1) println(k+" "+v)
  }

  def s2j(): Unit ={
    import scala.collection.JavaConversions
    import scala.collection.JavaConversions._
    import scala.collection.JavaConverters._
    var scalaList = ArrayBuffer[Int]()
    scalaList.+=(1)
    scalaList.+=(2)
    scalaList.+=(3)

    val javaList = scalaList.asJava
    javaList.add(4)
    for (i <- 0 until(javaList.size())) println(javaList.get(i))


    var scalaSet = scala.collection.mutable.HashSet[Int]()
    scalaSet.+=(1)
    scalaSet.+=(2)
    scalaSet.+=(3)

    val javaSet = scalaSet.asJava
    val javaSet1 = JavaConversions.mutableSetAsJavaSet(scalaSet)
    javaSet1.add(4)
    //因为javaSet是java.util.Set, 没有foreach方法, 所以需要将其转换为Scala的集合类型
    //这里转换成java类型之后，所有的集合都需要import scala.collection.JavaConversions._才能使用scala集合中的foreach
    //等于说将java类型又隐式转换成scala类型
    for (i <- javaSet1) println(i)
  }


}
