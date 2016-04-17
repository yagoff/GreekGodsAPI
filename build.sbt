name := "GreekGodsAPI"

version := "1.0"

scalaVersion := "2.11.8"

val akkaHttpVersion = "2.4.3"
val slickVersion = "3.1.1"
val circeVersion = "0.4.0"

resolvers += Resolver.bintrayRepo("hseeberger", "maven")

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.21",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "com.h2database" % "h2" % "1.3.176",
  "org.postgresql" % "postgresql" % "9.3-1104-jdbc41",
  "org.flywaydb" % "flyway-core" % "4.0",
  "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaHttpVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-jawn" % circeVersion,
  "de.heikoseeberger" %% "akka-http-circe" % "1.5.3"
)