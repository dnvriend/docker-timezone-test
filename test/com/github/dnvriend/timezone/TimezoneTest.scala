package com.github.dnvriend.timezone

import java.text.{ParseException, SimpleDateFormat}
import java.util.Date

import com.github.dnvriend.TestSpec
import controllers.com.github.dnvriend.timezone.Iso8601

class TimezoneTest extends TestSpec {

  object SameMomentInTime {
    val laTime        = "2015-01-01T04:00:00.000-08:00"

    val nyTime        = "2015-01-01T07:00:00.000-05:00"

    val utcTime       = "2015-01-01T12:00:00.000Z"

    val londonTime    = "2015-01-01T12:00:00.000+00:00"

    val amsterdamTime = "2015-01-01T13:00:00.000+01:00"

    val japanTime     = "2015-01-01T21:00:00.000+09:00"
  }

  import SameMomentInTime._

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

  // see: http://tempus-js.com/blog/the-iso-8601-101/
  "ISO-8601 date" should "parse formats" in {
    // ISO-8601 is very flexible in the formatting of the date; YYYY would be a valid ISO date,
    // as would YYYY-MM, YYYY-MM-DDTHH. You can supply the time zone, or omit it, up to you.
    // You can only omit going down though, so a date like MM-DD isn’t valid, because it is missing the year.

    // ISO-8601 is very flexible. You can also do crazy stuff like provide seconds to as many decimal places
    // as you want. So a date like 2009-09-09T09:09:09.00000000000000000009 is totally valid. ISO8601’s biggest
    // gain is also its biggest pain, which makes it very difficult to code a reliable parser for the standard.
    // Some small but annoying quirks to look out for are:

    // - Unlimited decimals allowed for seconds,
    // - The 'T' separator between the date and time can be pretty much anything
    // - time zone can be omitted, or Z,
    // - it can have a : to separate hours from minutes, or not.

    // Of course all of this also means that different languages can and will do different stuff with ISO8601. For example languages
    // like Python and .NET will use 6 decimal places (microseconds) for seconds, while languages like PHP and JavaScript will use 3.
    // Python allows you to change the ‘T’ separator at your whim, and also by default will omit any time zone data, not even add a Z,
    // meanwhile JavaScript and other languages will always use ‘T’ and if no time zone data is present, will use Z for the time zone.

    // 6 characters for decimal
    parse("2015-02-08T19:47:29.028751+01:00", "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX") shouldBe a[java.lang.Long]
    parse("2015-02-06T15:52:48.814+01:00") shouldBe a [java.lang.Long]
  }

  "ISO-8601 fullDateTimeWithMillis" should "be the same japan is 00:00:00 next day" in {
    val laTimePrevDay =    "2015-01-01T07:00:00.000-08:00" // note: 2015-01-01
    val japanTimeNextDay = "2015-01-02T00:00:00.000+09:00" // note: the next day!
    parse(laTimePrevDay) shouldBe parse(japanTimeNextDay)
  }

  "ISO-8601 formatted Zulu dateTime" should "not be the same as local time where assumption is that the timezone is CEST" in {
    val localTime = "2015-04-21T20:00:00"
    val timeUTC =   "2015-04-21T20:00:00Z" // 'Z' is canonical form for +00:00
    // timezone should not be GMT, else the localtime and zulu time could be the same,
    // when in Amsterdam.. oh well
    timezone match {
      case "GMT" => fail("Should not be GMT")
      case _ =>
    }
    parse(localTime, "yyyy-MM-dd'T'HH:mm:ss") should not be parse(timeUTC, "yyyy-MM-dd'T'HH:mm:ssXXX")
  }

  "ISO-8601 fullDateTime" should "london == amsterdam in epoch difference" in {
    // cannot be parsed because the time format is different (no millis)
    intercept[ParseException] {
      parse(londonTime, Iso8601.fullDateTimeWithSeconds) shouldBe parse(amsterdamTime, Iso8601.fullDateTimeWithSeconds)
    }
  }
}
