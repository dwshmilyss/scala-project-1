/**
  * Created by duanwei on 2017/3/13.
  */
object Test extends App{
  def test(): Int ={
    println("aa")
    return 1
  }

  def call_by_value(x:Int)={
    println(s"x1 = ${x}")
    println(s"x2 = ${x}")
  }

  def call_by_name(x: => Int)={
    println(s"x1 = ${x}")
    println(s"x2 = ${x}")
  }

//  call_by_value(test())
  call_by_name(test())


}
