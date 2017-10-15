package org.eu.fuzzy.swagger.javadoc

import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import com.sun.javadoc._
import com.sun.tools.doclets.standard.Standard
import org.eu.fuzzy.json.serialization._
import org.eu.fuzzy.swagger.PluginOptions
import org.eu.fuzzy.swagger.model.{Contact, Info, License, Swagger}

import scala.language.implicitConversions

/**
 * An entry point of JavaDoc plugin.
 */
object SwaggerDoclet extends Doclet {

  /**
   * Returns a latest version of Java Programming Language supported by this doclet.
   */
  def languageVersion() = LanguageVersion.JAVA_1_5

  /**
   * Returns a required number of arguments for the specified option.
   *
   * @param option  a name of option
   *
   * @return the number of arguments
   */
  def optionLength(option: String): Int = option match {
    case _ if option.startsWith("--api-") => 2
    case _ => Standard.optionLength(option)
  }

  /**
   * Validates a list of specified options.
   *
   * @param options   a list of options to validate
   * @param reporter  an interface to print any message
   *
   * @return true if the options are valid
   */
  def validOptions(options: Array[Array[String]], reporter: DocErrorReporter): Boolean = {
    val pluginOptions = PluginOptions(options)
    if (pluginOptions.destination.isEmpty) {
      reporter.printError("A destination directory cannot be empty")
      false
    }
    else if (pluginOptions.version.isEmpty) {
      reporter.printError("API version cannot be empty")
      false
    }
    else if (!pluginOptions.licenseUrl.isEmpty && pluginOptions.licenseName.isEmpty) {
      reporter.printError("API license name cannot be empty if the license URL is specified")
      false
    }
    else {
      Standard.validOptions(options, reporter)
    }
  }

  /**
   * Generates a Swagger resource listing.
   */
  def start(root: RootDoc): Boolean = {
    val options = PluginOptions(root.options())
    val document = getApiSpecification(options).toPrettyJson().getBytes(StandardCharsets.UTF_8)
    Files.write(getSwaggerPath(options), document)
    //root.classes().foreach(entry => JaxRsResource(entry))
    true
  }

  /**
   * Returns a path to the Swagger resource listing.
   */
  private def getSwaggerPath(options: PluginOptions) = if (options.destination.endsWith(".json"))
    Paths.get(options.destination)
  else
    Paths.get(options.destination, "swagger.json")

  /**
   * Ensures that the string isn't empty.
   */
  private def nonEmpty(value: String) = if (value.isEmpty) None else Some(value)

  /**
   * Ensures that the URL string isn't empty.
   */
  private def nonEmptyUrl(value: String) = nonEmpty(value).map(new URL(_))

  /**
   * Returns a contact information for the exposed API.
   */
  private def getApiContact(options: PluginOptions) =
    if (options.contactName.nonEmpty || !options.contactEmail.nonEmpty || !options.contactUrl.nonEmpty)
      Some(Contact(
        name = nonEmpty(options.contactName),
        url = nonEmptyUrl(options.contactUrl),
        email = nonEmpty(options.contactEmail)
      ))
    else
      None

  /**
   * Returns a license for the exposed API.
   */
  private def getApiLicense(options: PluginOptions) =
    if (options.licenseName.nonEmpty)
      Some(License(
        name = options.licenseName,
        url = nonEmptyUrl(options.licenseUrl),
      ))
    else
      None

  /**
   * Returns a metadata for the exposed API.
   */
  private def getApiInfo(options: PluginOptions) = Info(
    version = options.version,
    title = options.title,
    description = nonEmpty(options.description),
    termsOfService = nonEmpty(options.termsOfService),
    contact = getApiContact(options),
    license = getApiLicense(options)
  )

  /**
   * Returns an API specification document.
   */
  private def getApiSpecification(options: PluginOptions) = Swagger(
    info = getApiInfo(options),
    host = nonEmpty(options.host),
    basePath = nonEmpty(options.basePath),
    paths = Map.empty
  )

}
