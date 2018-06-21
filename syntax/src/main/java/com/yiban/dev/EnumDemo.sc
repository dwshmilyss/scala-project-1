
object EnumDemo{
  object Breed extends Enumeration{
    type Breed = Value
    val doberman = Value("DOBERMAN")
    val yorkie = Value("YORKIE")
    val scottie = Value("SCOTTIE")
    val dane = Value("DANE")
    val portie = Value("PORTIE")
  }

  //如果不在作用域中导入该类，那么引用的时候都要带上Breed
  import Breed._
  //打印所有值和ID
  println("ID\tBreed")
  for (breed <- Breed.values) {
    println(s"${breed.id}\t$breed")
  }
  println()
  //过滤
  Breed.values filter(_.toString.endsWith("IE")) foreach println
  //更简洁的过滤方法，符合函数式编程
  println()
  def isIE(breed: Breed) = breed.toString.endsWith("IE")
  Breed.values filter isIE foreach println

  object WeekDay extends Enumeration{
    type WeekDay = Value
    val Mon,Tue,Wed,Thu,Fri,Sat,Sun = Value
  }

  import WeekDay._
  def isWorkingDay(day:WeekDay) = !(day == Sat || day == Sun)
  println()
  WeekDay.values filter isWorkingDay foreach println
}

EnumDemo




