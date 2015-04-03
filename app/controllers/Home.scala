package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import controllers.Products._
import play.api.mvc.Action
import play.mvc.Controller
import ui.Pagelet

object Home extends Controller {

  def main = Action.async { request =>
    val leftFuture = LeftSide.index(embed = 1)(request)
    val rightFuture = RightSideStream.index()(request)

    for {
      leftPagelet <- leftFuture
      rightPagelet <- rightFuture

      leftBody <- Pagelet.readBody(leftPagelet)
      rightBody <- Pagelet.readBody(rightPagelet)
    } yield {
      Ok(views.html.home(leftBody, rightBody))
    }
  }
}