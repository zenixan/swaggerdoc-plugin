import Dependencies._

name := "Swagger Doclet"
organization := "org.eu.fuzzy"
description := "JavaDoc plugin to generate a Swagger resource listing."
version := "0.1.0"

//
// License details
//
licenses := Seq(
  ("MIT License", url("https://spdx.org/licenses/MIT.html"))
)

developers := List(
  Developer("zenixan", "Yevhen Vatulin", "zenixan@gmail.com", null)
)

//
// Other project settings
//
normalizedName := "swaggerdoc-plugin"
homepage := Some(url("https://github.com/zenixan/swaggerdoc-plugin"))
startYear := Some(2017)

//
// Project dependencies
//
resolvers += Resolver.mavenLocal
enablePlugins(JavaToolsPlugin)  // for DocLet API

/*assemblyExcludedJars := {
  (fullClasspath in assembly).map { classPath => classPath.filter(_.data.getName == "tools.jar") }
}.value*/

//
// Sub-modules
//
lazy val root = (project in file(".")).dependsOn(macros)
  .settings(commonBuildSettings: _*)
  .settings(libraryDependencies ++= dependencies)
  .settings(projectDependencies := Seq())  // ignore macro dependency

lazy val macros = (project in file("macros"))
  .settings(commonBuildSettings: _*)
  .settings(scalacOptions in (Compile, doc) ++= Seq("-Ymacro-no-expand"))
  .settings(libraryDependencies ++= dependencies)

//
// Build/Publish settings
//
assemblyJarName := normalizedName.value + "-assembly-" + version.value + ".jar"
lazy val commonBuildSettings = Seq(
  Keys.scalaVersion := Dependencies.scalaVersion,
  crossPaths := false,
  cancelable := true
)
