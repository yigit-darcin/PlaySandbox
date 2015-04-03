package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import service.ServiceClient

object RightSide extends Controller {

  def index(embed: Integer) = Action.async {
    val rightSideFuture = ServiceClient.makeServiceCall("rightSide")
    val rightSideFuture2 = ServiceClient.makeServiceCall("rightSide2")

    for {
      count <- rightSideFuture
      count2 <- rightSideFuture2

    } yield {
      if (embed == 1) {
        Ok(views.html.rightPartial(count))
      }
      else {
        Ok(views.html.rightside(count))
      }
    }
  }
}
