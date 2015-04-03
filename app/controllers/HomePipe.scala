package controllers

import play.api.mvc.{Action, Controller}
import service.ServiceClient
import ui.HtmlStreamImplicits._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import ui.{HtmlStream, Pagelet}

object HomePipe extends Controller {

  def main = Action { request =>

    val rightSide = ServiceClient.makeServiceCall("rightSide")
    val rightSide2 = ServiceClient.makeServiceCall("rightSide2")
    val content = ServiceClient.makeServiceCall("content")
    val leftSide = ServiceClient.makeServiceCall("leftSide")

    val rightSideStream = Pagelet.renderStream(rightSide.map(str => views.html.partial(str)), "rightSide1")
    val rightSideStream2 = Pagelet.renderStream(rightSide2.map(str => views.html.partial(str)), "rightSide2")
    val contentStream = Pagelet.renderStream(content.map(str => views.html.partial(str)), "content")
    val leftSideStream = Pagelet.renderStream(leftSide.map(str => views.html.partial(str)), "leftSide")

    val body = HtmlStream.interleave(contentStream, leftSideStream, rightSideStream, rightSideStream2)

    Ok.feed(views.stream.homePipe(body))
  }
}