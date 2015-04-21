package com.github.dnvriend.timezone

import com.github.dnvriend.TestSpec

class TimezoneTest extends TestSpec {

  "Timezone" should "not be empty" in {
    timezone should not be empty
  }

  it should "log the timezone" in {
    log.info("Timezone: {}", timezone)
  }

  it should "parse the timezone" in {
    getTime.futureValue shouldBe ""
  }
}
