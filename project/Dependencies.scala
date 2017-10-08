import sbt._

object Dependencies {

  // Versions
  val scalaVersion = "2.12.3"
  val jsonVersion = "3.5.0"
  val apiVersion = "2.1-m03"

  // Libraries
  val jsonDependency = "org.json4s" %% "json4s-native" % jsonVersion
  val apiDependency = "javax.ws.rs" % "javax.ws.rs-api" %  apiVersion
  val reflectDependency = "org.scala-lang" % "scala-reflect" % scalaVersion % "provided"

  // Common dependencies
  val dependencies = Seq(reflectDependency, jsonDependency, apiDependency)

}