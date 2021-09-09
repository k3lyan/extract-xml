package Model

import advxml.core.data.{ValidatedNelEx, XmlDecoder}
import advxml.implicits.AnyConverterSyntaxOps
import advxml.implicits._
import cats.syntax.all._
import play.api.libs.json.Json
import scala.xml.XML
import java.io.File
final case class Suppliers(suppliersInfo: List[SupplierInfo])

object Suppliers {
  implicit val suppliersCompanionDecoder: XmlDecoder[Suppliers] = XmlDecoder.of { suppliers =>
      $(suppliers).supplier.run[ValidatedNelEx].andThen { supplier =>
        supplier
          .map { supplier =>
            supplier.asValidated[SupplierInfo]
          }
          .sequence
          .map(_.toList)
      }.map(Suppliers.apply)
    }


  implicit val suppliersWrites = Json.writes[Suppliers]



  def getInfo(xmlFile: File): ValidatedNelEx[Suppliers] = XML.loadFile(xmlFile).decode[Suppliers]
}
