package controllers

import controllers.com.github.dnvriend.timezone.DateTimeJsonFormat
import play.api.mvc._

object Application extends Controller with DateTimeJsonFormat {

  def index() = Action {
    Ok(dateTimeAsJsonString).as(JSON)
  }

  def postDate() = play.mvc.Results.TODO
}