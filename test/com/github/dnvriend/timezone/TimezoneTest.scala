package com.github.dnvriend.timezone

import java.text.{ParseException, SimpleDateFormat}

import com.github.dnvriend.TestSpec
import controllers.com.github.dnvriend.timezone.Iso8601

class TimezoneTest extends TestSpec {

  val laTime        = "2015-01-01T04:00:00.000-08:00"

  val nyTime        = "2015-01-01T07:00:00.000-05:00"

  val utcTime       = "2015-01-01T12:00:00.000Z"

  val londonTime    = "2015-01-01T12:00:00.000+00:00"

  val amsterdamTime = "2015-01-01T13:00:00.000+01:00"

  val japanTime     = "2015-01-01T21:00:00.000+09:00"

  /**
   * Parses the received dateTime with UTC offset in hours/minutes and
   * returns the diff from epoch and UTC as a Long
   * @param dateTime
   * @param format
   * @return
   */
  def parse(dateTime: String, format: String = Iso8601.fullDateTimeWithMillis): Long = {
    val sdf = new SimpleDateFormat(format)
    sdf.parse(dateTime).getTime
  }

  "ISO-8601 fullDateTimeWithMillis" should "london == amsterdam in epoch difference" in {
    // londonTime converted to epoch long shouldBe the same as amsterdamTime from epoc
    // what is does, calculate to UTC and calc the diff from epoch
    // for both offsets it should be the same Long value
    parse(londonTime) shouldBe parse(amsterdamTime)
  }

  it should "utcTime == londonTime" in {
    parse(utcTime) shouldBe parse(londonTime)
  }

  it should "utcTime == amsterdamTime" in {
    parse(utcTime) shouldBe parse(amsterdamTime)
  }

  it should "laTime == japanTime" in {
    parse(laTime) shouldBe parse(japanTime)
  }

  "ISO-8601 fullDateTimeWithMillis" should "be the same japan is 00:00:00 next day" in {
    val laTime =    "2015-01-01T07:00:00.000-08:00" // note: 2015-01-01
    val japanTime = "2015-01-02T00:00:00.000+09:00" // note: the next day!
    parse(laTime) shouldBe parse(japanTime)
  }

  "ISO-8601 fullDateTime" should "london == amstedam in epoch difference" in {
    // cannot be parsed because the time format is different (no millis)
    intercept[ParseException] {
      parse(londonTime, Iso8601.fullDateTimeWithSeconds) shouldBe parse(amsterdamTime, Iso8601.fullDateTimeWithSeconds)
    }
  }
}
