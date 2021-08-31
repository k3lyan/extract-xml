package Presentation

import Model.Forecast
import cats.effect.IO

object CommandExecutor {
  def processCommand(cmd: Command, forecast: Forecast): IO[Unit] = cmd match {
    case PrintRaw => IO {
      forecast.suppliers.suppliersInfo.foreach(supplier => println(s"Supplier: ${supplier.name} - Age: ${supplier.age}"))
    }
    case PrintElapsed => IO {
      forecast.suppliers.suppliersInfo.foreach(supplier => println(s"Supplier: ${supplier.name} - Age at T: ${supplier.age + forecast.elapsedTime}"))
    }
    case Help => help
    case Unknown => IO{ println("Unknown (type 'h' for help)\n") }
  }

  private def help: IO[Unit] = {
    val text = """
                 |Possible commands
                 |-----------------
                 |r                - print raw data extracted from the XML
                 |e                - print data calculated with elapsed time T
                 |h                - show this help text
                 |q                - quit
        """.stripMargin
    IO(println(text))
  }
}
