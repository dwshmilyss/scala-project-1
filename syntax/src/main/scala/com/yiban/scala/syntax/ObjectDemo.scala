package com.yiban.scala.syntax

package objectDemo {

  class ObjectDemo {
    private val str: String = "class ObjectDemo"
    println(ObjectDemo.str1)
  }

  object ObjectDemo {
    private var str1: String = "object ObjectDemo"
    var objectDemo:ObjectDemo = _

    //创建单例的工厂方法
    def apply(): ObjectDemo = {
      if (objectDemo == null) {
        objectDemo = new ObjectDemo()
      }
      objectDemo
    }
    //如果要访问伴生类的私有成员，不能直接写在伴生对象里面
    //因为伴生对象是静态的，会先于apply()执行
    //那时候伴生类还没有实例化，所以会报NullPointException
    def test = {
      println(s"value = ${objectDemo.str}")
    }

    //    val str2 = objectDemo.str
  }

  object Main {
    def main(args: Array[String]): Unit = {
      ObjectDemo.objectDemo = ObjectDemo.apply()
      ObjectDemo.test
    }
  }
  private[objectDemo] class A {

  }
}


