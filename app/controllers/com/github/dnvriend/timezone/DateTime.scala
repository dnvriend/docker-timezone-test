package controllers.com.github.dnvriend.timezone

import java.text.SimpleDateFormat
import java.util.Date

import spray.json.DefaultJsonProtocol
import spray.json._

case class DateTime(date: String, millisSinceEpochGMT: Long, offsetUTC: String, timezone: String)

/**
 * I RFC-822 only allows the 8 North American time zones to be named (4 standard time, 4 daylight savings time);
 * for all others, you have to specify the hour offset.
 *
 * see: http://www.w3.org/Protocols/rfc822/
 */
object Rfc822 {
  val fullDateTimeWithMillis: String = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZ"
}

/**
 * ISO 8601 applies to representations and formats of dates in the Gregorian calendar, times based on the 24-hour
 * timekeeping system (including optional time zone information), time intervals and combinations thereof.
 *
 * see: http://en.wikipedia.org/wiki/ISO_8601
 */
object Iso8601 {
  val fullDateTimeWithMillis: String = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

  val fullDateTimeWithSeconds: String = "yyyy-MM-dd'T'HH:mm:ssXXX"

  val fullDateTimeWithMinutes: String = "yyyy-MM-dd'T'HH:mmXXX"
}

trait DateTimeJsonFormat extends DefaultJsonProtocol {

  // yes, I know, but I like spray-json
  implicit val dateTimeJsonFormat = jsonFormat4(DateTime)

  def dateTime: DateTime = {
    // ISO-8601 date-time with UTC timezone format: http://en.wikipedia.org/wiki/ISO_8601
    val sdf = new SimpleDateFormat(Iso8601.fullDateTimeWithMillis)

    // If no UTC relation information is given with a time representation, the time is assumed to be in local time.
    // While it may be safe to assume local time when communicating in the same time zone, it is ambiguous when used in
    // communicating across different time zones. It is usually preferable to indicate a time zone (zone designator) using the
    // standard's notation.

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

    // for xml, the xs:dateTime has the following definition: http://www.w3.org/TR/xmlschema-2/#dateTime
    // It follows the ISO-8601 standard, in which the timezone is encodes UTC with Z, and offset +/-00:00 (hr:min)

    DateTime(sdf.format(new Date(millisSinceEpochGMT)), millisSinceEpochGMT, offsetUTC, timezone)
  }

  /**
   * see: https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
   * see: http://www.w3schools.com/schema/schema_dtypes_date.asp
   * see: http://en.wikipedia.org/wiki/Time_zone
   * @return ThreeLetterISO8601TimeZone: Sign TwoDigitHours : Minutes
   */
  def offsetUTC: String = {
    val sdf = new SimpleDateFormat("ZZZ")
    sdf.format(new Date)
  }

  /**
   * Returns the timezone
   * @return
   */
  def timezone: String = {
    val sdf = new SimpleDateFormat("z")
    sdf.format(new Date)
  }

  def dateTimeAsJsonString = dateTime.toJson.compactPrint
}