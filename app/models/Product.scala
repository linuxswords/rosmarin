package models

import play.api.Logger
import play.api.libs.json._
import play.api.libs.functional.syntax._
import collection.JavaConversions._
import collection.JavaConverters._
import scala.collection.immutable.HashMap

case class Feature(label: String, value: String)

object Feature{

  def convertFromXml(node: xml.Node): Feature = {
    Feature(
      node \ "label" text,
      node \ "value" text
    )
  }

  def unapply(m: Map[String, Any]): Option[Feature] = try{
    Option(Feature(
      label = m("label").asInstanceOf[String],
      value = m("value").asInstanceOf[String]
    )
    )
  } catch {
    case ex: Exception =>None
  }

  def mapApply(m: Map[String, Any]): Feature = {
    Feature.unapply(m).get
  }

  implicit val featureWrites: Writes[Feature] = new Writes[Feature] {
    override def writes(o: Feature): JsValue =
    Json.obj(
      "label" -> o.label,
      "value" -> o.value
    )

  }

  implicit val featureReads: Reads[Feature] =  (
    ( __ \ "label").read[String] and
      ( __ \ "value").read[String]
    )(Feature.apply _)

}


/**
  * Created by knm on 05.07.16.
  */
case class Product(
                  availability: String,
                  category: String,
                  categoryid: String,
                  cnetid: String,
                  code: String,
                  currency: String,
                  deliverymode: String,
                  description: String,
                  ean: String,
                  employeediscount: String,
                  feature: Seq[Feature],
                  imageurl: String,
                  location: String,
                  manufacturer: String,
                  manufactureraid: String,
                  maxorderquantity: String,
                  merchant: String,
                  name: String,
                  price: BigDecimal,
                  shortdescription: String,
                  stock: BigDecimal
//                  vat: String,
//                  warranty: Int,
//                  weight: BigDecimal,
//                  weightunit: String
                  )
object Product{

  def convertFromXml(node: xml.Node): Product = {
    Product(

      node \ "availability" text,
      node \ "category" text,
      node \ "categoryid" text,
      node \ "cnetid" text,
      node \ "code" text,
      node \ "currency" text,
      node \ "deliverymode" text,
      node \ "description" text,
      node \ "ean" text,
      node \ "employeediscount" text,
      (node \\ "feature" map Feature.convertFromXml),
      node \ "imageurl" text,
      node \ "location" text,
      node \ "manufacturer" text,
      node \ "manufactureraid" text,
      node \ "maxorderquantity" text,
      node \ "merchant" text,
      node \ "name" text,
      BigDecimal(node \ "price" text),
      node \ "shortdescription" text,
      BigDecimal(node \ "stock" text)
//      node \ "vat" text,
//      (node \ "warranty" text).toInt,
//      BigDecimal(node \ "weight" text),
//      node \ "weightunit" text
    )
  }

  import collection.JavaConversions._

  def unapply(m: Map[String, Any]): Option[Product] = try {
    val price = if(m("price").isInstanceOf[Int]) m("price").asInstanceOf[Int] else m("price").asInstanceOf[Double]

    val features: Seq[Feature] =  m("feature").asInstanceOf[java.util.ArrayList[java.util.HashMap[String,String]]] match {
      case null => Nil
      case list if list.isEmpty => Nil
      case list => {
        list.map{ aMap => Feature(aMap("label"),aMap("value"))}
      }
    }

    Option(Product(
      m("availability").asInstanceOf[String],
      m("category").asInstanceOf[String],
      m("categoryid").asInstanceOf[String],
      m("cnetid").asInstanceOf[String],
      m("code").asInstanceOf[String],
      m("currency").asInstanceOf[String],
      m("deliverymode").asInstanceOf[String],
      m("description").asInstanceOf[String],
      m("ean").asInstanceOf[String],
      m("employeediscount").asInstanceOf[String],
      features,
      m("imageurl").asInstanceOf[String],
      m("location").asInstanceOf[String],
      m("manufacturer").asInstanceOf[String],
      m("manufactureraid").asInstanceOf[String],
      m("maxorderquantity").asInstanceOf[String],
      m("merchant").asInstanceOf[String],
      m("name").asInstanceOf[String],
      price,
      m("shortdescription").asInstanceOf[String],
      m("stock").asInstanceOf[Int]
    ))

  }
  catch {
      case ex: Exception => {
        Logger.error("error: " + ex.getCause)
        Logger.error("error: " + ex.getMessage)
        ex.printStackTrace
        None
      }
    }


  def mapApply(m: Map[String, Any]): Product = {
    Product.unapply(m).get
  }

  implicit val pReads: Reads[Product] =  (
      ( __ \ "availability").read[String] and
      ( __ \ "category").read[String] and
      ( __ \ "categoryid").read[String] and
      ( __ \ "cnetid").read[String] and
      ( __ \ "code").read[String] and
      ( __ \ "currency").read[String] and
      ( __ \ "deliverymode").read[String] and
      ( __ \ "description").read[String] and
      ( __ \ "ean").read[String] and
      ( __ \ "employeediscount").read[String] and
      ( __ \ "feature").read[Seq[Feature]] and
      ( __ \ "imageurl").read[String] and
      ( __ \ "location").read[String] and
      ( __ \ "manufacturer").read[String] and
      ( __ \ "manufactureraid").read[String] and
      ( __ \ "maxorderquantity").read[String] and
      ( __ \ "merchant").read[String] and
      ( __ \ "name").read[String] and
      ( __ \ "price").read[BigDecimal] and
      ( __ \ "shortdescription").read[String] and
      ( __ \ "stock").read[BigDecimal]
    )(Product.apply _)

  implicit  val productWrites: Writes[Product] = {
    new Writes[Product] {
      override def writes(o: Product): JsValue = {
        Json.obj(
          "availability" -> o.availability,
          "category" -> o.category,
          "categoryid" -> o.categoryid,
          "cnetid" -> o.cnetid,
          "code" -> o.code,
          "currency" -> o.currency,
          "deliverymode" -> o.deliverymode,
          "description" -> o.description,
          "ean" -> o.ean,
          "employeediscount" -> o.employeediscount,
          "feature" -> o.feature,
          "imageurl" -> o.imageurl,
          "location" -> o.location,
          "manufacturer" -> o.manufacturer,
          "manufactureraid" -> o.manufactureraid,
          "maxorderquantity" -> o.maxorderquantity,
          "merchant" -> o.merchant,
          "name" -> o.name,
          "price" -> o.price,
          "shortdescription" -> o.shortdescription,
          "stock" -> o.stock
//          "vat" -> o.vat,
//          "warranty" -> o.warranty,
//          "weight" -> o.weight,
//          "weightunit" -> o.weightunit
        )
      }
    }
  }
}

