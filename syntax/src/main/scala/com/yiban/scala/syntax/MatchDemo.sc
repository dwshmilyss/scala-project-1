import scala.collection.{Iterator, Seq}

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
    //这是专门匹配序列的语法。:+ 因为在中间，所以这种写法又叫中缀表达式。
    //其实这里相当于是调用了:+.unapply
    //    case head +: tail => s"$head +: " + seqToString(tail)
    //上面那句也可以写成这样
    case head +: tail => s"$head +: ${seqToString(tail)}"
    case Nil => "Nil"
  }

  for {
    seq <- Seq(nonEmptySeq, emptySeq, nonEmptyList, emptyList, nonEmptyVector, emptyVector, nonEmptyMap.toSeq, emptyMap.toSeq)
  } {
    println(seqToString(seq = seq))
  }

  /**
    * 由于函数采用了尾递归调用 所以一定要在方法签名中显式的指定返回值类型
    *
    * @param seq
    * @tparam T
    */
  def processSeq2[T](seq: Seq[T]): Unit = seq match {
    // +:其实是一个object 里面只有一个方法unapply 在scala中有很多符号命名的对象
    case +:(head, tail) =>
      printf("%s +: ", head)
      processSeq2(tail)
    case Nil => print("Nil\n")
  }

  println("=====================")
  processSeq2(List(1, 2, 3, 4, 5))

  println("=====================")
  val BookExtractorRE = """Book:title=([^,]+),\s+author=(.+)""".r
  val MagazineExtractorRE = """Magazine:title=([^,]+),\s+issue=(.+)""".r

  val catalog = Seq(
    "Book:title=scala second edition, author=dean wampler",
    "Magazine:title=the new yorker, issue=2014 acb",
    "Unknown:title=who put this there???"
  )
    println(1111)
  for (item <- catalog) {
    item match {
      case BookExtractorRE(title, author) =>
        println(s"""book "$title,written by $author""")
      case MagazineExtractorRE(title, issue) =>
        println(s"""magazine "$title,written by $issue""")
      case entry => println(s"unknown entry : $entry")
      case _ => "Unknown"
    }

  }

  println(22222)

  println("==========绑定变量===========")

  case class Address(street: String, city: String, country: String)

  case class Person(name: String, age: Int, address: Address)

  val dw = Person("dw", 20, Address("one", "shanghai", "china"))
  val bob = Person("bob", 21, Address("two", "beijing", "china"))
  val james = Person("james", 22, Address("three", "nanjing", "china"))

  for (person <- Seq(dw, bob, james)) {
    person match {
      //p @ ...的语法把整个Person对象赋值给变量p
      case p@Person("dw", 20, address) => println(s"hi dw! ${p}")
      case p@Person("bob", 21, a@Address(street, city, country)) =>
        println(s"hi ${p.name} ,age : ${p.age}, in ${a.city}")
      case p@Person(name, age, _) => println(s"who are you ,$age year-old person named $name? $p")
    }
  }

  println("=========匹配泛型集合============")
  //case语句中无法匹配泛型集合，因为scala也是跑在JVM上的，而运行时JVM会擦除泛型类型
  //解决方法是：嵌套匹配，首先匹配到集合，然后在内层匹配元素类型
  def doSeqMatch[T](seq: Seq[T]): String = seq match {
    case Nil => "Nothing"
    case head +: _ => head match {
      case _:Double => "Double"
      case _:String => "String"
      case _ => "Unknown"
    }
  }

  val res = for (item <- Seq(List(1.1, 2.2, 3.3), List("a", "b"), Nil)) yield{
    item match {
      case seq:Seq[_] => (s"seq ${doSeqMatch(seq)}",seq)
      case _ => ("unknown!",item)
    }
  }
  println(res)


  println("=============== 匹配可变参数 ==================")
  case class WhereIn[T](vals: T*)
  val wheres = Seq(WhereIn(1, 2, 3, 4), WhereIn("a", "b", "c"))
  for (where <- wheres) {
    where match {
      //@ _* 匹配case类中的可变参数  注意和下面集合中的可变参数区分开
      case WhereIn(vals@_*) => println(vals)
      case _ => println(None)
    }
  }

  println("-----------------------")
  val map = Map("one" -> 1, "two" -> 2, "three" -> 3)
  val list = List(1, 2, 3, 4, 5)

  def windows[T](seq: Seq[T]): String = seq match {
    //匹配集合中不确定的元素个数时用 _* 表示一个或多个  注意和case类中的可变参数区分开
    case Seq(head1, head2, _*) =>
      s"($head1,$head2), " + windows(seq.tail)
    case Seq(head, _*) =>
      s"($head,_), " + windows(seq.tail)
    case Nil => "Nil"
  }

  for (seq <- Seq(list, emptyList, map.toSeq)) {
    println(windows(seq))
  }

  println("================ end ======================")
}
MatchDemo