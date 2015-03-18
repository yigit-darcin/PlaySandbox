package models

case class Product(ean: Long, name: String)

object Product {

  var products = Set(
    Product(1L, "1. kitap"),
    Product(2L, "2. kitap"),
    Product(3L, "3. kitap"),
    Product(4L, "4. kitap")
  )

  def findAll = products.toList.sortBy(_.ean);

}