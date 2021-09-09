package Controllers

import Model.{Forecast, Suppliers}
import cats.data.Validated.{Invalid, Valid}
import cats.data.ValidatedNel
import cats.implicits._

import java.io.File
import java.nio.file.{Files, Paths}
import scala.util.Try

final case class Validator(args: List[String]) {
  type AllErrorsOr[A] = ValidatedNel[ArgumentError, A]

  val validateNumberOfArguments: AllErrorsOr[Unit] =
    if (args.length == 2) ().validNel
    else ArgumentError(s"Wrong number of arguments: ${args.length}", ArgumentsNumberInvalid).invalidNel

  val validateXMLFile: AllErrorsOr[File] =
    if (Files.exists(Paths.get(args.head))) new File(args.head).validNel
    else ArgumentError(s"${args.head} is not a valid file path.", XMLFileInvalid).invalidNel

  val validateloadSuppliers: AllErrorsOr[Suppliers] =
    validateXMLFile.andThen(file => Suppliers.getInfo(file)) match {
      case Valid(suppliers) => suppliers.validNel
      case Invalid(err) => ArgumentError(s"Could not load XML properly.", LoadXMLInvalid).invalidNel
    }

  val validateElapsedTime: AllErrorsOr[Int] = Try(args.tail.head.toInt).toEither.left.map(ex => ArgumentError(ex.getMessage, ElapsedTimeInvalid)).toValidatedNel

  val apply: AllErrorsOr[Forecast] = (validateNumberOfArguments, validateloadSuppliers, validateElapsedTime).mapN((_, validSuppliers, validElaspsedTime) => Forecast(validSuppliers, validElaspsedTime))
}
