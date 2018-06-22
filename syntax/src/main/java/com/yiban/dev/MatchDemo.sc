/**
  * match一般都要有一个default，以免漏匹配
  * match也可以返回一个值，编译器会推断所有case子句的返回值类型的最近公共父类型作为返回值类型
  * 编写case子句时，编译器假定大写字母开头是类型，小写的是变量
  */
object MatchDemo {
  println("==========一个简单的匹配===========")
  val bools = Seq(true, false)
  for (bool <- bools) {
    bool match {
      case true => println("true")
      case false => println("false")
    }
  }
  println("=====================")
  for {
    x <- Seq(1, 2, 3.7, "one", "two", "three")
  } {
    val str = x match {
      case 1 => "int 1"
      case i: Int => "other int : " + i
      case d: Double => "double : " + d
      case "one" => "string one"
      case s: String => "other string : " + s
      //以下两个语句作用相同，都是在没有匹配到之后指定一个Any的默认项
      //      case unexpected => "unexpected value : "+unexpected
      case _ => "unexpected value : " + x
    }
    println(str)
  }

  println("=========带参数时的match============")

  /**
    * 带参数时的match
    *
    * @param y 如果要在case语句中使用参数名，要加波浪线
    */
  def checkY(y: Int) = {
    for (x <- Seq(99, 100, 101)) {
      val str = x match {
        case `y` => "found y !" //这里使用的是参数y 而不是重新定义局部变量
        case i: Int => "int : " + i
      }
      println(str)
    }
  }

  checkY(100)

  println("=====================")
  for {
    x <- Seq(1, 2, 3.7, "one", "two", "three")
  } {
    val str = x match {
      //如果处理逻辑相同 可以写在同一行
      case _: Int | _: Double => "a number : " + x
      case "one" => "string one"
      case _: String => "other string : " + x
      case _ => "unexpected value : " + x
    }
    println(str)
  }
  println("==========序列的匹配===========")
  /**
    * 序列的匹配
    */
  val nonEmptySeq = Seq(1, 2, 3, 4, 5)
  val emptySeq = Seq.empty[Int]
  val nonEmptyList = List(1, 2, 3, 4, 5)
  val emptyList = Nil
  val nonEmptyVector = Vector(1, 2, 3, 4, 5)
  val emptyVector = Vector.empty[Int]
  val nonEmptyMap = Map("one" -> 1, "two" -> 2, "three" -> 3, "four" -> 4, "five" -> 5)
  val emptyMap = Map.empty[String, Int]

  def seqToString[T](seq: Seq[T]): String = seq match {
//    case head +: tail => s"$head +: " + seqToString(tail)
      //上面那句也可以写成这样
    case head +: tail => s"$head +: ${seqToString(tail)}"
    case Nil => "Nil"
  }

  for {
    seq <- Seq(nonEmptySeq,emptySeq,nonEmptyList,emptyList,nonEmptyVector,emptyVector,nonEmptyMap.toSeq,emptyMap.toSeq)
  }{
    println(seqToString(seq = seq))
  }
}

MatchDemo