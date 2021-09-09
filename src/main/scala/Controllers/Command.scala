package Controllers

trait Command
case object PrintRaw extends Command
case object PrintElapsed extends Command
case object PrintJson extends Command
case object Quit extends Command
case object Help extends Command
case object Unknown extends Command

object Command {

  def parse(input: String): Command = input match {
    case "r" => PrintRaw
    case "e" => PrintElapsed
    case "j" => PrintJson
    case "h" | "help" => Help
    case "q" | "quit" => Quit
    case _ => Unknown
  }

}