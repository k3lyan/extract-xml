package Model

import advxml.core.data.Converter
import advxml.core.data.ValidatedConverter
import advxml.core.data.ValidatedNelEx
import advxml.core.transform.XmlContentZoomRunner
import advxml.implicits.AnyConverterSyntaxOps
import cats.data.Validated.Invalid
import cats.data.Validated.Valid
import cats.implicits.catsSyntaxOptionId
import cats.implicits.catsSyntaxValidatedId

import scala.xml.NodeSeq

trait ConvertersInstances {

  implicit val intValidatedConverter: ValidatedConverter[XmlContentZoomRunner, Int] =
    Converter.of[XmlContentZoomRunner, ValidatedNelEx[Int]] { zoomRunner =>
      zoomRunner.validated match {
        case Valid(str) =>
          str.toIntOption match {
            case Some(number) =>
              number.valid
            case None =>
              new Exception(s"'$str' is not a number").invalidNel
          }
        case Invalid(error) =>
          new Exception(s"Could not read property '$zoomRunner' \n $error").invalidNel
      }
    }

  implicit def optionFromNodeSeqConverter[T](implicit converter: ValidatedConverter[NodeSeq, T]): ValidatedConverter[NodeSeq, Option[T]] =
    Converter.of { nodeSeq =>
    if (nodeSeq.isEmpty) {
      Option.empty[T].valid
    } else {
      nodeSeq.asValidated[T].map(_.some)
    }
  }
}
