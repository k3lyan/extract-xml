import Model.Forecast
import Presentation.CommandExecutor.processCommand
import cats.data.Validated.{Invalid, Valid}
import cats.effect.{ExitCode, IO, IOApp, Sync}
//import com.typesafe.scalalogging.StrictLogging

import Model.Suppliers
import Presentation.{Command, CommandExecutor, Quit}
import cats.data.NonEmptyList
import cats.implicits.catsSyntaxFlatMapOps
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.SelfAwareStructuredLogger
import org.typelevel.log4cats.slf4j.Slf4jLogger


object Main extends IOApp {

  implicit private def unsafeLogger[F[_] : Sync]: SelfAwareStructuredLogger[F] = Slf4jLogger.getLogger[F]

  val prompt = "Command ('h' for help, 'q' to quit)\n==> "

  private def cli(forecast: Forecast): IO[ExitCode] = for {
    _     <- IO(print(prompt))
    cmd   <- IO(scala.io.StdIn.readLine()).map(Command.parse)
    _     <- cmd match {
      case Quit => IO.unit
      case _ => processCommand(cmd, forecast) >> cli(forecast)
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
    (println(args.length))
    if (args.length != 2) Logger[IO].error("Wrong number of arguments").as(ExitCode.Error)
    else {
      (args.head, args.tail.head) match
      {
        case (path, elapsedTime) => IO(Suppliers.getInfo(path))
          .flatMap {
            case Valid(suppliers) => cli(Forecast(suppliers, elapsedTime.toInt))
            case Invalid(errors) => reportErrors(errors)
          }
        case _ => IO(System.err.println("Argument is missing to launch correctly this program.")).as(ExitCode.Error)
      }
    }
  }
}
