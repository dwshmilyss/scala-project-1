package com.yiban.dev

import java.io.FileInputStream
import java.util.Properties

object ConfigFactory {
  def load() = {
    val properties = new Properties()
    val path = Thread.currentThread().getContextClassLoader.getResource("params.properties").getPath
    println(path)
    properties.load(new FileInputStream(path))
    println(properties.getProperty("name"))
  }

  def main(args: Array[String]): Unit = {
    load()
  }

}
