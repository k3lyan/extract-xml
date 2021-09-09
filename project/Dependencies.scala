import sbt._

object Versions {
  val advxml        = "2.4.0"
  val scalatest     = "3.2.9"
  val playJson      = "2.9.2"
  val akka          = "2.6.16"
  val akkaHttp      = "10.2.6"
  val log4Cats      = "1.2.1"
  val scalaLogging  = "3.9.2"
  val log4Cats2     = "2.1.1"
}

object Library {
  val advxml          = "com.github.geirolz" %% "advxml-core" % Versions.advxml
  val playJson       = "com.typesafe.play" %% "play-json" % Versions.playJson
  val scalatest      = "org.scalatest" %% "scalatest" % Versions.scalatest
  val akkaHttp       = "com.typesafe.akka" %% "akka-http" % Versions.akkaHttp
  val akkaSl4j       = "com.typesafe.akka" %% "akka-slf4j" % Versions.akka
  val httpSpray      = "com.typesafe.akka" %% "akka-http-spray-json" % Versions.akkaHttp
  val akkaHttpXml    = "com.typesafe.akka"              %% "akka-http-xml" % Versions.akkaHttp
  val akkaStream     = "com.typesafe.akka"              %% "akka-stream" % Versions.akka
  val log4Cats       = "org.typelevel"                  %% "log4cats-core"              % Versions.log4Cats
  val log4CatsSlf4j  = "org.typelevel"                  %% "log4cats-slf4j"             % Versions.log4Cats
  val scalaLogging   = "com.typesafe.scala-logging"     %% "scala-logging"              % Versions.scalaLogging
  val log4Cats2      = "io.chrisdavenport"              %% "log4cats-core"              % Versions.log4Cats2
  val log4CatsSlf4j2 = "io.chrisdavenport"              %% "log4cats-slf4j"             % Versions.log4Cats2
  val logback        = "ch.qos.logback" % "logback-classic" % "1.2.3"
}

object Dependencies {
  import Library._

  val forecast = Seq(
    advxml,
    playJson,
    akkaHttp,
    akkaSl4j,
    akkaStream,
    akkaStream,
    log4Cats,
    log4CatsSlf4j,
    logback,
    //log4Cats2,
    //log4CatsSlf4j2,
    scalaLogging,
    scalatest % Test
  )
}
