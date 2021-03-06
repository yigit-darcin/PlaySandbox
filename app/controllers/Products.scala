package controllers

import models.Product
import play.api.cache.Cache
import play.api.Play.current
import play.api.data.Form
import play.api.data.Forms.{longNumber, mapping, nonEmptyText}
import play.api.i18n.Messages
import play.api.mvc.{Flash, Action, Controller}
import securesocial.core.{Identity, Authorization, SecureSocial}

object Products extends Controller with SecureSocial {

  def list = Action { implicit request =>

    val products = Cache.getOrElse("products") {
      println("from db")
      Product.findAll
    }

    Ok(views.html.list(products))

  }


  def securedList = SecuredAction { implicit request =>

    val products = Product.findAll

    Ok(views.html.list(products))
  }

  case class WithProvider(provider: String) extends Authorization {
    def isAuthorized(user: Identity) = {
      user.identityId.providerId == provider
    }
  }

  def show(ean: Long) = Action { implicit request =>
    Product.findById(ean).map { product =>
      Ok(views.html.detail(product))
    }.getOrElse(NotFound)
  }

  private val productForm: Form[Product] = Form(
    mapping(
      "ean" -> longNumber.verifying(
        "validation.ean.duplicate", Product.findById(_).isEmpty),
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
    )(Product.apply)(Product.unapply)
  )

  def newProduct = Action { implicit request =>
    val form = if (flash.get("error").isDefined)
      productForm.bind(flash.data)
    else
      productForm

    Ok(views.html.edit(form))
  }

  def save = Action { implicit request =>
    val newProductForm = productForm.bindFromRequest()

    newProductForm.fold(
      hasErrors = { form =>
        Redirect(routes.Products.newProduct()).
          flashing(Flash(form.data) +
          ("error" -> Messages("validation.errors")))
      },
      success = { newProduct =>
        Product.add(newProduct)
        val message = Messages("products.new.success", newProduct.name)
        Redirect(routes.Products.show(newProduct.ean)).
          flashing("success" -> message)
      }
    )
  }

}
