package controllers

import java.io.File
import java.util.UUID
import javax.inject._

import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.{ElasticClient, ElasticsearchClientUri, RichSearchResponse}
import models.elasticsearch.BulkInsert
import org.elasticsearch.common.settings.Settings
import play.api.libs.json.Json
import play.api.mvc._

import scala.collection.JavaConversions._
import scala.concurrent.{ExecutionContext, Future}


/**
  * Created by knm on 05.07.16.
  */
@Singleton
class Siroop  @Inject() extends Controller {

  val client = ElasticClient.transport(ElasticsearchClientUri("localhost", 9300))
  
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

  def siroop(index: String, typez: String,  term: String) = Action {

    val response = client.execute{ search in index / typez query term }
    val products = extractProducts(response)
    Ok(products.toString)
  }

  def extractProducts(searchResponse: Future[RichSearchResponse])(implicit executor: ExecutionContext) =
    extractTsFromSearchResponse(models.Product.apply)(searchResponse)

  def extractTsFromSearchResponse[T](applyFunc: Map[String, AnyRef] => T)(searchResponse: Future[RichSearchResponse])(implicit executor: ExecutionContext): Future[Seq[T]] = {
    searchResponse.map(_.getHits.hits().map(_.sourceAsMap.toMap).map(applyFunc))
  }


}
