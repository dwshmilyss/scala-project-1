package com.yiban.scala.syntax.order

/**
  * Created by 10000347 on 2016/6/8.
  */
object OrderTest{
  def main(args: Array[String]) {
//    println(new Pair_Implicits(7,9).bigger)
    case class Person(val name:String,val age:Int)
    implicit val KeyOrdering = new Ordering[Person] {
      override def compare(x: Person, y: Person): Int = {
        x.age.compareTo(y.age)
      }
    }

//    implicit def ordering[A <: Person]: Ordering[A] = Ordering.by(_.age)

//    implicit def ordering[A <: Person]: Ordering[A] = new Ordering[A] {
//      override def compare(x: A, y: A): Int = {
//        x.age.compareTo(y.age)
//      }
//    }

    val person1 = new Person("dw",20)
    val person2 = new Person("aa",21)
    val person3 = new Person("bb",22)

    val treeSet = new scala.collection.mutable.TreeSet[Person]()
    treeSet.+=(person1)
    treeSet.+=(person3)
    treeSet.+=(person2)

//    val linkedList = new scala.collection.mutable.LinkedList[Person]()
//    linkedList.+:(person3)
//    linkedList.+:(person2)
//    println(linkedList.elem)
//
//
//    val linkedHashSet = new scala.collection.mutable.LinkedHashSet[Person]()
//    linkedHashSet.+=(person1)
//    linkedHashSet.+=(person3)
//    linkedHashSet.+=(person2)
//    println(linkedHashSet.size)

    for (person <- treeSet){
      println(person.toString)
    }
  }




}

class Pair_Implicits[T:Ordering](val first:T,val second:T){
  def bigger(implicit ordered: Ordering[T]) = {
    if (ordered.compare(first,second) > 0) first else second
  }
}
