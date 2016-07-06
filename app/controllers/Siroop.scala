package controllers

import javax.inject._
import models.elasticsearch.BulkInsert
import play.api._
import play.api.libs.json.Json
import play.api.mvc._

import scala.collection.immutable

/**
  * Created by knm on 05.07.16.
  */
@Singleton
class Siroop  @Inject() extends Controller {


  def createJson() = Action {

    val products = getProducts

    // validate / filter products here

    Ok(Json.toJson(products))
  }

  def getProducts: Seq[models.Product] = {
    val xml = scala.xml.XML.load("/home/knm/projects/siroop-el/feed.xml")
    val productsAsXml = xml \\ "product"

    val products = productsAsXml map models.Product.convertXmlToJson
    products
  }

  def createBulk(index: String, `type`: String) = Action {

    val products = getProducts

    val bulkInsert = BulkInsert(products, "product", "siroop")

    Ok(bulkInsert.bulkExport.mkString("\n"))


  }


}
