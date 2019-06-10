package com.yiban.scala.syntax

/**
  * Created by 10000347 on 2016/5/24.
  */
object RegTest extends App{
  val numPattern = "[0-9]+".r
  val wsnumwsPattern = """\s+[0-9]+\s+""".r
  for (matchStr <- numPattern.findAllIn("99 bottles,98 bottles")) println(matchStr)
  //找到匹配的第一个元素，返回是Some()
  val ml = numPattern.findFirstIn("99 bottles,98 bottles")
  println(ml.get.toString)
  //匹配字符串的开始部分，返回是Some()
  val ml1 = numPattern.findPrefixOf("98 bottles,99 bottles")
  println(ml1.get.toString)

  //找到第一个匹配的 替换
  numPattern.replaceFirstIn("a99 bottles,98 bottles","xx").foreach(print)
  println
  //找到所有匹配的并替换
  numPattern.replaceAllIn("99 bottles,98 bottles","xx").foreach(print)

}
