package com.yiban.scala.syntax;

case class Point(x: Double = 0.0, y: Double = 0.0)

abstract class Shape() {
  def draw(f: String => Unit): Unit = f(s"draw:${this.toString}")
}

case class Circle(center: Point, radius: Double) extends Shape

case class Rectangle(lowerLeft: Point, height: Double, width: Double) extends Shape

case class Triangle(point1: Point, point2: Point, point3: Point) extends Shape


object Main{
  def main(args: Array[String]): Unit = {
    val shape : Shape = new Circle(Point.apply(2.0,1.0),2.0)
    shape.draw(str => println(s"hello : $str"))
  }
}