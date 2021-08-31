package Model

import advxml.core.data.{ValidatedNelEx, XmlDecoder}
import advxml.implicits.{AnyConverterSyntaxOps, XmlContentZoomSyntaxForId}
import advxml.implicits._
import advxml.core.data.{ValidatedConverter, XmlDecoder}
import cats.syntax.all._

import java.io.File
import scala.xml.{Elem, XML}

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

  def getInfo(xmlFile: String): ValidatedNelEx[Suppliers] = XML.loadFile(xmlFile).decode[Suppliers]
}
