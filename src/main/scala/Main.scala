import Controllers.Validator
import Presentation.Console.{cli, reportErrors}
import cats.data.Validated.{Invalid, Valid}
import cats.effect.{ExitCode, IO, IOApp, Sync}

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    IO { Validator(args).apply }
        .flatMap {
          case Valid(forecast) => cli(forecast)
          case Invalid(e) => reportErrors(e)
        }
}
