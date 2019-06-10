package com.yiban.scala.syntax.xiebian

/**
  * Created by 10000347 on 2016/6/1.
  * 逆变
  */
object Test extends App{
  class Person
  class Student extends Person

  val person1:Person = new Person
  val person2:Person = new Person
  val person3:Person = new Person

  val stu1:Student = new Student
  val stu2:Student = new Student
  val stu3:Student = new Student


//  val personList = List[Person](person1,person2,person3)
//  val studentList : List[Student] = personList
  val studentList = List[Student](stu1,stu2,stu3)
  val personList : List[Person] = studentList


}
