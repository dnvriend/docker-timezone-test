package com.github.dnvriend.timezone

import com.github.dnvriend.TestSpec

class Java8DateTimeTest extends TestSpec {

  // see: https://github.com/reactivecodes/scala-time
  // see: http://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html

  "Scalatime" should "create LocalDate" in {
    import codes.reactive.scalatime._
    val localDate = LocalDate.of(2014, 6, 7)
    localDate.getYear shouldBe 2014
    localDate.getMonth shouldBe Month.June
    localDate.getDayOfMonth shouldBe 7
  }

  it should "create duration from literal using implicits" in {
    import codes.reactive.scalatime._
    import codes.reactive.scalatime.Scalatime._
    val period: java.time.Duration = 3.minutes
    period.toMillis shouldBe 1000 * 60 * 3
  }

  it should "create period via Period and duration via Duration objects" in {
    import codes.reactive.scalatime._
    import java.time.{Duration => JavaDuration, Period => JavaPeriod}

    // A period is a date-based amount of time in the ISO-8601 calendar system,
    // such as '2 years, 3 months and 4 days'.
    val period: Period = Period.days(1)

    period shouldBe JavaPeriod.ofDays(1)

    // A duration is a time-based amount of time, such as '34.5 seconds'.
    val duration: Duration = Duration.days(1)

    duration shouldBe JavaDuration.ofDays(1)
  }

  it should "create ZonedDateTime" in {
    import codes.reactive.scalatime._
    val zonedDateTime = ZonedDateTime()
    zonedDateTime.getZone shouldBe ZoneId.UTC
    // 2015-04-22T05:23:35.199Z
  }
}
