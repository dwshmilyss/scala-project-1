package com.yiban.scala.syntax

import scala.collection.mutable.ArrayBuffer


/**
  * Created by 10000347 on 2016/6/22.
  * +: ++: 即使是可变的集合，调用这样的方法也会生成新的集合对象
  * += ++= 如果是可变集合，不会生成新的集合对象，只会在原集合中追加元素，注意和+: ++:的区别
  * +:是把元素添加到集合的开头 :+是把元素添加到集合的末尾
  */
object CollectionTest {
  def main(args: Array[String]) {
    //        val list = List(1, 2, 3, 4, 5)
    //        //    println(list.head)
    //        println("===========")
    //        //    println(list.tail)
    //        //    println(list.tail.head)
    //        //    println(list.tail.tail)
    //        println(Nil.+:("a").+:("b"))
    //        println(Nil.+("a").+("b").isInstanceOf[String])
    //        //    println(Nil.++:(List("a", "b")))
    //        println("c".++:(List("a", "b", "c"))) //这样拼接之后得到的是Vector
    //        println("c".++(List("a", "b", "c"))) //这样拼接之后得到的是Vector
    //        println("===========")
    //        //    println(Nil.++(List("a", "b")))
    //        println(List("a", "b", "c").++("c"))
    //        println(List("a", "b", "c").++:("c"))
    //        //    println(List("a", "b").::("c"))
    //        //    println(List("a", "b").:::(List("c")))
    //        println("===========")

    //    for(ele <- List.fill(10)(scala.util.Random.nextInt(10))) println(ele)
    //    for (ele <- List(1 to 5:_*)) println(ele)

    //    scala2Java()
    //    testFold()
    //    testQueue()
//    testStack()
    List("a","b","C").indices.zip(List("a","b","C"))
  }

  /**
    * java集合转换为scala的
    */
  def java2Scala(): Unit = {
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


    val javaSet = new java.util.HashSet[Int]
    javaSet.add(1)
    javaSet.add(2)
    javaSet.add(3)

    val scalaSet = javaSet.asScala
    for (i <- scalaSet) println(i)

    val scalaSet1 = JavaConversions.asScalaSet(javaSet)
    for (i <- scalaSet1) println(i)

    val javaMap = new java.util.HashMap[String, Int]
    javaMap.put("a", 1)
    javaMap.put("b", 2)
    javaMap.put("c", 3)

    val scalaMap = javaMap.asScala
    for ((k, v) <- scalaMap) println(k + " " + v)

    val scalaMap1 = JavaConversions.mapAsScalaMap(javaMap)
    for ((k, v) <- scalaMap1) println(k + " " + v)
  }

  /**
    * scala集合转成java的
    */
  def scala2Java(): Unit = {
    import scala.collection.JavaConversions
    import scala.collection.JavaConversions._
    import scala.collection.JavaConverters._
    //scala的list转换成java的list
    var scalaList = ArrayBuffer[Int]()
    scalaList.+=(1)
    scalaList.+=(2)
    scalaList.+=(3)

    //这里转成java的List后就可以使用java List的方法了
    val javaList = scalaList.asJava
    javaList.add(4)
    println("scala_List to java_List =========== start ")
    /**
      * 下面的方法也都可以把scala的List转成java的List
      */
    //    for (i <- 0 until (javaList.size())) println(javaList.get(i))
    //    for (ele <- javaList) println(ele)
    val javaList1 = JavaConversions.seqAsJavaList(scalaList)
    javaList1.add(5)
    val javaList2 = JavaConversions.asJavaCollection(scalaList)
    javaList2.add(6)
    for (ele <- javaList2) println(ele)
    println("scala_List to java_List =========== end ")

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

  /**
    * 测试fold foldLeft foldRight /: \:
    * fold(z:B)(op:(B,B) => B)
    * z是初始化参数，op是一个函数，第一个参数是z 第二个参数是集合中的每一个元素
    * 然后第一次执行的时候让z和集合中的第一个元素进行op函数操作，返回的结果再作为op的第一个参数，依次执行和集合中的每个元素
    * fold里面调用的就是foldLeft 所以两者等价
    * foldLeft和foldRight的区别就是遍历集合的顺序 left是从左到右 right是从右到左
    */
  def testFold() = {
    println(List("a", "b", "c").fold("e")(_ + _))

    /**
      * 结果:eabc
      * /: 等价于 foldLeft
      */
    println(List("a", "b", "c").foldLeft("e")((a, b) => a + b))
    println(List("a", "b", "c")./:("e")((a, b) => a + b))

    /**
      * foldRight比较复杂一点  因为集合是从最右侧的元素开始操作的
      * 下面的操作步骤如下：
      * 1、"c"+"e"
      * 2、"b"+("c"+"e")
      * 3、"a"+("b"+("c"+"e"))
      * 最终结果：abce
      * :\ 等价于 foldRight
      */
    println(List("a", "b", "c").foldRight("e")((a, b) => a + b))
    println(List("a", "b", "c").:\("e")((a, b) => a + b))
  }


  def testQueue(): Unit = {
    val queue = collection.immutable.Queue()
    val queue1 = queue.enqueue(0).enqueue(List(1, 2, 3, 4))
    println("test immutable queue =============== start")

    /**
      * dequeue 返回一个元组(队列的第一个元素,队列(其余元素))
      */
    println(queue1.dequeue._1)
    println(queue1.dequeue._2)

    val (first, others) = queue1.dequeueOption.getOrElse(None)
    println(first + " " + others)
    println("test immutable queue =============== end")

    println("test mutable queue =============== start")
    var queue2 = collection.mutable.Queue[Int]()
    queue2.+=(1, 2, 3)
    queue2.++=(List(4, 5, 6))
    // +: :+ ++:
    var queue3 = queue2.+:(4)
    var queue4 = queue3.++:(List(5, 6))
    var queue5 = queue4.:+(7)
    println(queue2)
    println(queue3)
    println(queue3.getClass)
    println(queue4.getClass)
    println(queue5.getClass)
    println("test mutable queue =============== end")
  }

  /**
    * 注意mutable和immutable两者的pop的区别
    * immutable的pop返回的是剩余的元素
    * 而mutable的pop返回的是pop出来的值
    */
  def testStack(): Unit = {
    val stack = collection.immutable.Stack()
    val s2 = stack.push(10, 20, 30)
    //head 表示最后一个元素 因为是栈结构
    println(s2.head)
    //pop 调用后返回的剩下元素
    println(s2.pop)
    println(s2.pop.pop.head)

    var stack1 = collection.mutable.Stack[Int](10,20,30)
    stack1.push(40).push(50)
    println("test mutable Stack ========== start ")
    println(stack1.head)
    println(stack1.pop())
    println(stack1.pop())
    println(stack1.pop())
    println(stack1.pop())
    println(stack1.pop())
    println("test mutable Stack ========== end ")
  }

  def testStream(): Unit ={
    val st = 1 #:: 2 #:: 3 #:: Stream.empty[Int]

  }
}
