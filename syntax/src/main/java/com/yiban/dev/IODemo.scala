package com.yiban.dev

import java.io.{FileInputStream, FileWriter, FileOutputStream}
import java.nio.ByteBuffer

import scala.io.Source

object IODemo {
  def main(args: Array[String]) {
    //读文件
    println(Source.fromFile("d:/r_ggplot2_test.txt").mkString)
    //逐行读取
    Source.fromFile("d:/r_ggplot2_test.txt").getLines().foreach(println)

    //写文件(第二个boolean参数代表是否追加)
    val channel = new FileOutputStream("d:\\aaa.txt",true).getChannel
    channel write ByteBuffer.wrap("中国人\n".getBytes())
    channel.close()
    //or
    val out = new FileWriter("d:\\aaa.txt",true)
    out.write("中国人\n")
    out.close()


    //复制文件
    val inC  = new FileInputStream("d:\\aaa.txt").getChannel
    val outC = new FileOutputStream("d:\\bbb.txt").getChannel
    inC transferTo (0, inC.size, outC)

    //网络IO
    import java.net.{URL, URLEncoder}
    import scala.io.Source.fromURL
    println(fromURL(new URL("http://www.baidu.com")).mkString)
//    或者指定编码：
    fromURL(new URL("http://www.baidu.com"))(scala.io.Codec.UTF8).mkString
  }


}
