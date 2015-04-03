package controllers

import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.concurrent.Promise
import play.api.mvc.{Action, Controller, SimpleResult}

import scala.concurrent.Future

object Mock extends Controller {

  def mock(serviceName: String) = Action.async {

    serviceName match {
      case "rightSide" => respond(data = "in 210 milliseconds which is fast !", delay = 210)
      case "rightSide2" => respond(data = "4210", delay = 4210)
      case "leftSide" => respond(data = "in 1000 milliseconds which is ok !", delay = 1000)
      case "content" => respond(data = "in 6210 milliseconds, it was damn slow", delay = 6210)
    }
  }

  private def respond(data: String, delay: Long): Future[SimpleResult] = {
    Promise.timeout(Ok(data), delay)
  }
}