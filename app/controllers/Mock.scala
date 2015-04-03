package controllers

import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.concurrent.Promise
import play.api.mvc.{Action, Controller, SimpleResult}

import scala.concurrent.Future

object Mock extends Controller {

  def mock(serviceName: String) = Action.async {

    serviceName match {
      case "rightSide" => respond(data = "210", delay = 210)
      case "rightSide2" => respond(data = "4210", delay = 4210)
      case "leftSide" => respond(data = "1000", delay = 1000)
    }
  }

  private def respond(data: String, delay: Long): Future[SimpleResult] = {
    println("Trying to get a result for " + data + " with delay " + delay)
    Promise.timeout(Ok(data), delay)
  }
}