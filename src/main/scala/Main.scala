import cats.data.Validated.{Invalid, Valid}
import cats.effect.{ExitCode, IO, IOApp, Sync}
import org.typelevel.log4cats.SelfAwareStructuredLogger
import com.typesafe.scalalogging.StrictLogging
import org.typelevel.log4cats.slf4j.Slf4jLogger
import Model.{Suppliers}

object Main extends IOApp with StrictLogging {

  implicit private def unsafeLogger[F[_] : Sync]: SelfAwareStructuredLogger[F] = Slf4jLogger.getLogger[F]

  private def printInfo(path: String): IO[Unit] = IO {
    Suppliers.getInfo(path) match {
      case Valid(output) => output.suppliers.foreach(supplier => println(s"Supplier: ${supplier.name} - Age: ${supplier.age}"))
      case Invalid(errors) => println(errors)
    }
  }

  def run(args: List[String]): IO[ExitCode] = {
    args.headOption match {
      case Some(path) => printInfo(path).as(ExitCode.Success)
      case None => IO(System.err.println("An error occured")).as(ExitCode(2))
    }
  }
}
