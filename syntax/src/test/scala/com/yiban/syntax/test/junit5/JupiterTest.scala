package com.yiban.syntax.test.junit5

import org.junit.{After, Before}
import org.junit.jupiter.api.{AfterAll, AfterEach, BeforeAll, BeforeEach, Test}

class JupiterTest {

  @BeforeEach
  def init():Unit = {
    println("BeforeEach")
  }
  @AfterEach
  def close():Unit = {
    println("AfterEach")
  }


  @Test
  def test():Unit = {
    println("test jupiter")
  }

  @Test
  def test1():Unit = {
    println("test jupiter1")
  }
}
object JupiterTest {

  @BeforeAll
  def before():Unit = {
    println("BeforeAll")
  }

  @AfterAll
  def after():Unit = {
    println("AfterAll")
  }
}