package com.github.dnvriend

import java.io.File

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import controllers.com.github.dnvriend.timezone.DateTimeJsonFormat
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers, TestFailedException}
import play.api.libs.ws.WS
import play.api.{DefaultApplication, Mode}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class TestSpec extends FlatSpec with Matchers with BeforeAndAfterAll with ScalaFutures with DateTimeJsonFormat {
  implicit val p = PatienceConfig(timeout = 50.seconds)
  implicit val system: ActorSystem = ActorSystem("TestSystem")
  implicit val ec: ExecutionContext = system.dispatcher
  implicit val log: LoggingAdapter = Logging(system, this.getClass)
  implicit val application = new DefaultApplication(new File("."), this.getClass.getClassLoader, None, Mode.Test)

  def getTime(port: String): Future[String] = WS
    .url(s"http://boot2docker:$port")
    .get()
    .map(_.body)

  type :=>[A, B] = PartialFunction[A, B]
  def assertPf[T](pf: T :=> Unit, t: T) = if(!pf.isDefinedAt(t)) throw new TestFailedException("Unexpected: " + t.toString, failedCodeStackDepth = 0)

  override protected def afterAll(): Unit = {
    system.shutdown()
    system.awaitTermination()
  }
}
