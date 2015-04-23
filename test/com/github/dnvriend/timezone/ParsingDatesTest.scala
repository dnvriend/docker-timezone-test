package com.github.dnvriend.timezone

import java.text.SimpleDateFormat
import com.github.dnvriend.TestSpec
import scala.util.Try

class ParsingDatesTest extends TestSpec {

  def parse(date: String): Try[Long] =
    Try {
      println(s"$date: dateTime6")
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX").parse(date).getTime
    }.recover { case _ =>
      println(s"$date: dateTime3")
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(date).getTime
    }.recover { case _ =>
      println(s"$date: dateTZ")
      new SimpleDateFormat("yyyy-MM-ddXXX").parse(date).getTime
    }.recover { case _ =>
      println(s"$date: date")
      new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime
    }

  /**
   * For XML date formats see: http://www.w3schools.com/schema/schema_dtypes_date.asp
   * The following code restricts the xs:date and xs:dateTime format to the ones above,
   * which should be flexible enough to carry date and dateTime information and can still
   * be parsed by the server.
   */
  "ISO-8601 formats" should "parse date with no timezone" in {
    parse("2015-01-01") should be a 'success
  }

  it should "parse date UTC" in {
    parse("2015-01-01Z") should be a 'success
  }

  it should "parse date with UTC offset" in {
    parse("2015-01-01+02:00") should be a 'success
  }

  it should "parse dateTime UTC with millis (3)" in {
    parse("2015-01-01T12:00:00.000Z") should be a 'success
  }

  it should "parse dateTime UTC with millis (6)" in {
    parse("2015-01-01T12:00:00.000000Z") should be a 'success
  }

  it should "parse DateTime with UTC offset millis (3)" in {
    parse("2015-01-01T12:00:00.000+02:00") should be a 'success
  }

  it should "parse dateTime with UTC offset millis (6)" in {
    parse("2015-01-01T12:00:00.000000+02:00") should be a 'success
  }

  "ISO-8601 Zulu time" should "not be parsed with the wrong format" in {
    intercept[java.text.ParseException] {
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX").parse("2015-01-01T12:00:00.000Z")
    }
  }

  it should "parse with the correct format" in {
    Try(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse("2015-01-01T12:00:00.000Z")) should be a 'success
  }
}
