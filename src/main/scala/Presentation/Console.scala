package Presentation

import Controllers.{ArgumentError, Command, Quit}
import Model.Forecast
import Controllers.CommandExecutor.processCommand
import cats.data.NonEmptyList
import cats.effect.{ExitCode, IO}
import cats.implicits.catsSyntaxFlatMapOps
import org.typelevel.log4cats.slf4j.Slf4jLogger

object Console {
  val prompt = "Command ('h' for help, 'q' to quit)\n==> "

  def cli(forecast: Forecast): IO[ExitCode] = for {
    logger <- Slf4jLogger.create[IO]
    _     <- logger.info(prompt)
    cmd   <- IO(scala.io.StdIn.readLine()).map(Command.parse)
    _     <- cmd match {
      case Quit => IO.unit
      case _ => processCommand(cmd, forecast) >> cli(forecast)
    }
  } yield ExitCode.Success

  def reportErrors(err: NonEmptyList[ArgumentError]): IO[ExitCode] = IO {err.toList.foreach(error => println(s"\n${error.errorType}: ${error.value}"))}.as(ExitCode.Error)
}
