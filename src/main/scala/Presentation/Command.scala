package Presentation

import Model.Suppliers

trait Command
case class GetRawInfo(suppliers: Suppliers) extends Command
case class GetInfoWithElapsedTime(suppliers: Suppliers) extends Command
case object Quit extends Command
case object Help extends Command
case object Unknown extends Command

object Command {

  def parse(s: String, suppliers: Suppliers): Command = s match {
    case "brut" => GetRawInfo(suppliers)
    case "elapsed" => GetInfoWithElapsedTime(suppliers)
    case "h" | "help" => Help
    case "q" | "quit" => Quit
    case _ => Unknown
  }

}