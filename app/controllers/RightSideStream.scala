package controllers

import play.api.mvc.{Action, Controller}
import service.ServiceClient
import ui.HtmlStreamImplicits._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import ui.{HtmlStream, Pagelet}

object RightSideStream extends Controller {

  def index = Action {

    val rightSideCount = ServiceClient.makeServiceCall("rightSide")
    val rightSideCount2 = ServiceClient.makeServiceCall("rightSide2")

    val rightSideStream = Pagelet.renderStream(rightSideCount.map(str => views.html.rightPartial(str)), "count")
    val rightSideStream2 = Pagelet.renderStream(rightSideCount2.map(str => views.html.rightPartial2(str)), "count2")

    val body = HtmlStream.interleave(rightSideStream, rightSideStream2)

    Ok.feed(views.stream.rightsideStream(body))
  }
}