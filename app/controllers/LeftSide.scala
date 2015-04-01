package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import service.ServiceClient

object LeftSide extends Controller {

  def index(embed: Integer) = Action.async {
    val leftSideFuture = ServiceClient.makeServiceCall("leftSide")

    for {
      count <- leftSideFuture

    } yield {
      if (embed == 1) {
        Ok(views.html.leftPartial(count))
      }
      else {
        Ok(views.html.leftside(count))
      }
    }
  }
}
