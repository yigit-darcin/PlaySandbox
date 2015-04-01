package service

import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.ws.WS

import scala.concurrent.Future

object ServiceClient {

  def makeServiceCall(serviceName: String): Future[String] = {
    WS.url(s"http://localhost:9000/mock/$serviceName").get().map(_.body)
  }
}