name := "GreekGodsAPI"

version := "1.0"

scalaVersion := "2.11.8"

val akkaHttpVersion = "2.4.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaHttpVersion
)