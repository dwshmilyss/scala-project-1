package com.yiban.syntax.test.scalatest

import org.scalatest.{FeatureSpec, GivenWhenThen}

/**
 * FeatureSpec主要用于验收测试，包括促进程序员与非程序员一起工作以定义验收要求的过程。
 */
class FeatureSpecTest extends FeatureSpec with GivenWhenThen {
  class TVSet {
    private var on: Boolean = false

    def isOn: Boolean = on

    def pressPowerButton() {
      on = !on
    }
  }

  info("As a TV set owner")
  info("I want to be able to turn the TV on and off")
  info("So I can watch TV when I want")
  info("And save energy when I'm not watching TV")

  feature("TV power button") {
    scenario("User presses power button when TV is off") {

      Given("a TV set that is switched off")
      val tv = new TVSet
      assert(!tv.isOn)

      When("the power button is pressed")
      tv.pressPowerButton()

      Then("the TV should switch on")
      assert(tv.isOn)
    }

    scenario("User presses power button when TV is on") {

      Given("a TV set that is switched on")
      val tv = new TVSet
      tv.pressPowerButton()
      assert(tv.isOn)

      When("the power button is pressed")
      tv.pressPowerButton()

      Then("the TV should switch off")
      assert(!tv.isOn)
    }
  }
}
