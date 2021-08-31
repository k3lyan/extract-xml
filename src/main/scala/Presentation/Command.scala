package Presentation

import Model.{Forecast, Suppliers}

trait Command
case object PrintRaw extends Command
case object PrintElapsed extends Command
case object Quit extends Command
case object Help extends Command
case object Unknown extends Command

object Command {

  def parse(input: String): Command = input match {
    case "r" => PrintRaw
    case "e" => PrintElapsed
    case "h" | "help" => Help
    case "q" | "quit" => Quit
    case _ => Unknown
  }

}