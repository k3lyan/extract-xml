import cats.data.Validated.{Invalid, Valid}
import cats.effect.{ExitCode, IO, IOApp, Sync}
import org.typelevel.log4cats.{Logger, SelfAwareStructuredLogger}
import com.typesafe.scalalogging.StrictLogging
import org.typelevel.log4cats.slf4j.Slf4jLogger
import Model.Suppliers
import Presentation.{Command, CommandExecutor, Quit}
import cats.data.NonEmptyList
import cats.implicits.catsSyntaxFlatMapOps

object Main extends IOApp with StrictLogging {

  implicit private def unsafeLogger[F[_] : Sync]: SelfAwareStructuredLogger[F] = Slf4jLogger.getLogger[F]

  private def printInfo(path: String): IO[Unit] = IO {
    Suppliers.getInfo(path) match {
      case Valid(output) => output.suppliers.foreach(supplier => println(s"Supplier: ${supplier.name} - Age: ${supplier.age}"))
      case Invalid(errors) => println(errors)
    }
  }

  val prompt = "Command ('h' for help, 'q' to quit)\n==> "

  private def cli(suppliers: Suppliers): IO[ExitCode] = for {
    _     <- IO(print(prompt))
    cmd   <- IO(scala.io.StdIn.readLine()).map(s => Command.parse(s, suppliers))
    _     <- cmd match {
      case Quit => IO.unit
      case _ => CommandExecutor.processCommand(cmd, suppliers) >> cli(suppliers)
    }
  } yield ExitCode.Success



  private def reportErrors(errors: NonEmptyList[Throwable]): IO[ExitCode] =
    (for {
      errorMessages <- errors.traverse { error =>
        Logger[IO].error(error)(error.getMessage).as(error.getMessage)
      }
      printableError = errorMessages.toList.mkString("\n")
    } yield printableError).flatMap { printableError =>
      IO(throw new Exception(s"The Application failed with the following errors: \n$printableError")).as(ExitCode(1))
    }

  def run(args: List[String]): IO[ExitCode] = {
    args.headOption match {
      case Some(path) => IO(Suppliers.getInfo(path))
        .flatMap {
          case Valid(output) => cli(output)
          case Invalid(errors) => reportErrors(errors)
        }
      case None => IO(System.err.println("Argument is missing to launch correctly this program.")).as(ExitCode(2))
    }
  }
}
