package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import service.ServiceClient
import play.api.libs.iteratee.Enumerator

object RightSideEnumerator extends Controller {

  def index = Action {
    val rightSideFuture = ServiceClient.makeServiceCall("rightSide")
    val leftSideFuture = ServiceClient.makeServiceCall("leftSide")

    val leftSideEnum = Enumerator.flatten(leftSideFuture.map(str => Enumerator(str + "\n")))
    val rightSideEnum = Enumerator.flatten(rightSideFuture.map(str => Enumerator(str + "\n")))

    val body = leftSideEnum.andThen(rightSideEnum)

    Ok.chunked(body)
  }
}
