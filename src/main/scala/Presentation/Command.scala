package Presentation

import Model.Suppliers

trait Command
case object PrintRaw extends Command
case object PrintElapsed extends Command
case object Quit extends Command
case object Help extends Command
case object Unknown extends Command

object Command {

  def parse(s: String, suppliers: Suppliers): Command = s match {
    case "r" => PrintRaw
    case "e" => PrintElapsed
    case "h" | "help" => Help
    case "q" | "quit" => Quit
    case _ => Unknown
  }

}