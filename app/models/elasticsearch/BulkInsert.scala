package models.elasticsearch

import play.api.libs.json.{Json, JsValue, Writes}

/**
  * Created by knm on 05.07.16.
  */
case class BulkInsert(products: Seq[models.Product], `type`: String, index: String) {

  // should have access to real id here!
  val withId = products.zipWithIndex

  // delivers pairs of action + document
  def bulkExport: Seq[String] = {
    // { "index" : { "_index" : "test", "_type" : "type1", "_id" : "1" } }
    withId map { case(product, ind) =>
      val action = Json.obj(
        "index" -> Json.obj(
          "_index" -> index,
          "_type" -> `type`,
          "_id" -> ind.toString
        )
      )
      val prod = Json.toJson(product).toString


        action.toString + "\n" + prod
    }
  }
}
