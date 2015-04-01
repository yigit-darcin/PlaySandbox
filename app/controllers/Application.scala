package controllers

import play.api.mvc.{Action, Controller}

object Application extends Controller {

  def index = Action {
    Redirect (routes.Home.main())
  }

}