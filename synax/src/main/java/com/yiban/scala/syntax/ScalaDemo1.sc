class Upper{
  def upper(strs:String*):String={
//    strs.map(_.toUpperCase)
    strs.map((x:String) => x.toUpperCase()).mkString("[",",","]")
  }
}

val up = new Upper
println(up.upper("hello","world"))