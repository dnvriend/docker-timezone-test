package controllers.com.github.dnvriend.timezone

import java.text.SimpleDateFormat
import java.util.Date

import spray.json.DefaultJsonProtocol
import spray.json._

case class DateTime(date: String, dateAsLong: Long, timezone: String)

trait DateTimeJsonFormat extends DefaultJsonProtocol {
  implicit val dateTimeJsonFormat = jsonFormat3(DateTime)

  def dateTime: DateTime = {
    val sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ")
    val date = new Date
    DateTime(sdf.format(date), date.getTime, timezone)
  }

  /**
   * see: https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
   * see: http://www.w3schools.com/schema/schema_dtypes_date.asp
   * @return ThreeLetterISO8601TimeZone: Sign TwoDigitHours : Minutes
   */
  def timezone: String = {
    val sdf = new SimpleDateFormat("ZZZ")
    sdf.format(new Date)
  }

  def dateTimeAsJsonString = dateTime.toJson.compactPrint
}