package controllers.com.github.dnvriend.timezone

import java.text.SimpleDateFormat
import java.util.Date

import spray.json.DefaultJsonProtocol
import spray.json._

case class DateTime(date: String, millisSinceEpochGMT: Long, offsetUTC: String, timezone: String)

trait DateTimeJsonFormat extends DefaultJsonProtocol {
  implicit val dateTimeJsonFormat = jsonFormat4(DateTime)

  def dateTime: DateTime = {
    val sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ")

    // UTC – The World's Time Standard
    // Coordinated Universal Time (UTC) is the basis for civil time today. This 24-hour time standard is kept using highly precise
    // atomic clocks combined with the Earth's rotation.

    // A Standard, Not a Time Zone
    // UTC is the time standard commonly used across the world. The world's timing centers have agreed to keep their time scales closely
    // synchronized - or coordinated - therefore the name Coordinated Universal Time

    // Please read: http://www.timeanddate.com/time/aboututc.html

    // see: http://developer.android.com/reference/java/util/Date.html
    // new Date.getTime => A specific moment in time, with millisecond precision. Values typically come from currentTimeMillis(), and are always UTC,
    // regardless of the system's time zone. This is often called "Unix time" or "epoch time" which is the number of milliseconds
    // since January 1, 1970, 00:00:00 GMT. Note, GMT was the standard before 1972

    // Instances of this class are suitable for comparison, but little else. Use DateFormat to format a Date for display to a human. Use Calendar to break
    // down a Date if you need to extract fields such as the current month or day of week, or to construct a Date from a broken-down time. That is: this
    // class' deprecated display-related functionality is now provided by DateFormat, and this class' deprecated computational functionality is now provided
    // by Calendar. Both of these other classes (and their subclasses) allow you to interpret a Date in a given time zone.
//    val millisSinceEpochGMT = date.getTime

    // System.currentTimeMillis() returns the time since epoch. new Date.getTime returns the same, but there is a slight lag,
    // because an object must be initialized.
    val millisSinceEpochGMT = System.currentTimeMillis()

    DateTime(sdf.format(new Date(millisSinceEpochGMT)), millisSinceEpochGMT, offsetUTC, timezone)
  }

  /**
   * see: https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
   * see: http://www.w3schools.com/schema/schema_dtypes_date.asp
   * @return ThreeLetterISO8601TimeZone: Sign TwoDigitHours : Minutes
   */
  def offsetUTC: String = {
    val sdf = new SimpleDateFormat("ZZZ")
    sdf.format(new Date)
  }

  def timezone: String = {
    val sdf = new SimpleDateFormat("z")
    sdf.format(new Date)
  }

  def dateTimeAsJsonString = dateTime.toJson.compactPrint
}