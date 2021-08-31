package Presentation

import Model.Suppliers
import cats.effect.IO

object CommandExecutor {
  def processCommand(cmd: Command, data: Suppliers): IO[Unit] = cmd match {
    case PrintRaw => IO {
      data.suppliers.foreach(supplier => println(s"Supplier: ${supplier.name} - Age: ${supplier.age}"))
    }
    case PrintElapsed => IO {
      data.suppliers.foreach(supplier => println(s"Supplier: ${supplier.name} - Age at T: ${supplier.age + 10}"))
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
