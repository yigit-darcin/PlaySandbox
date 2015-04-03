package controllers

import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.concurrent.Promise
import play.api.mvc.{Action, Controller, SimpleResult}

import scala.concurrent.Future

object Mock extends Controller {

  def mock(serviceName: String) = Action.async {

    serviceName match {
      case "rightSide" => respond(data = "This appears in 0.4 milliseconds which is fast !", delay = 410)
      case "rightSide2" => respond(data = "This one comes in 4.2 seconds, sorry !", delay = 4210)
      case "leftSide" => respond(data = "This one is in 1 seconds which is ok !", delay = 1000)
      case "content" => respond(data = "Sorry mate, this shows up in 6.5 seconds, it was damn slow", delay = 6210)
    }
  }

  private def respond(data: String, delay: Long): Future[SimpleResult] = {
    Promise.timeout(Ok(data), delay)
  }
}