package Model
import advxml.implicits.{AnyConverterSyntaxOps, XmlContentZoomSyntaxForId}
import advxml.implicits._
import advxml.core.data.XmlDecoder
import cats.syntax.all._
import play.api.libs.json.Json


final case class SupplierInfo(name: String, age: Int)

object SupplierInfo extends ConvertersInstances {
  implicit val converter: XmlDecoder[SupplierInfo] = XmlDecoder.of { supplier => (
    supplier.attr("name").asValidated[String],
    supplier.attr("age").asValidated[Int]
    ).mapN(SupplierInfo.apply)
  }

  implicit val supplierInfoWrites = Json.writes[SupplierInfo]
}
