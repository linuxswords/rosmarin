package models

import play.api.libs.json.{Json, JsValue, Writes}

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
                  stock: BigDecimal,
                  vat: String,
                  warranty: Int,
                  weight: BigDecimal,
                  weightunit: String
                  )
object Product{

  def convertXmlToJson(node: xml.Node): Product = {
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
      node \\ "feature" map Feature.convertXmlToJson,
      node \ "imageurl" text,
      node \ "location" text,
      node \ "manufacturer" text,
      node \ "manufactureraid" text,
      node \ "maxorderquantity" text,
      node \ "merchant" text,
      node \ "name" text,
      BigDecimal(node \ "price" text),
      node \ "shortdescription" text,
      BigDecimal(node \ "stock" text),
      node \ "vat" text,
      (node \ "warranty" text).toInt,
      BigDecimal(node \ "weight" text),
      node \ "weightunit" text
    )
  }

  implicit  val ProductWrites: Writes[Product] = {
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
            "stock" -> o.stock,
            "vat" -> o.vat,
            "warranty" -> o.warranty,
            "weight" -> o.weight,
            "weightunit" -> o.weightunit

        )
      }
    }
  }
}


case class Feature(label: String, value: String)

object Feature{


  def convertXmlToJson(node: xml.Node): Feature = {
    Feature(
      node \ "label" text,
      node \ "value" text
    )
  }
  implicit val featureWrites: Writes[Feature] =
    new Writes[Feature] {
      override def writes(o: Feature): JsValue = {
        Json.obj(
          "label" -> o.label,
          "value" -> o.value
        )
      }
    }
}