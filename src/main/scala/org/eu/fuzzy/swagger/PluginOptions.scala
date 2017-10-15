package org.eu.fuzzy.swagger

import org.eu.fuzzy.meta.MapReader
import scala.reflect.runtime.universe._

/**
 * Represents a set of plugin options to generate a Swagger document.
 *
 * @param destination A destination directory where the generated Swagger file will be saved
 * @param title [[org.eu.fuzzy.swagger.model.Info.title]]
 * @param description [[org.eu.fuzzy.swagger.model.Info.description]]
 * @param version [[org.eu.fuzzy.swagger.model.Info.version]]
 * @param termsOfService [[org.eu.fuzzy.swagger.model.Info.termsOfService]],
 * @param contactName [[org.eu.fuzzy.swagger.model.Contact.name]]
 * @param contactUrl [[org.eu.fuzzy.swagger.model.Contact.url]]
 * @param contactEmail [[org.eu.fuzzy.swagger.model.Contact.email]]
 * @param licenseName [[org.eu.fuzzy.swagger.model.License.name]]
 * @param licenseUrl [[org.eu.fuzzy.swagger.model.License.url]]
 * @param host [[org.eu.fuzzy.swagger.model.Swagger.host]]
 * @param basePath [[org.eu.fuzzy.swagger.model.Swagger.basePath]]
 */
case class PluginOptions(
    @OptionName("-d")
    destination: String,
    @OptionName("-doctitle")
    title: String,
    @OptionName("--api-description")
    description: String,
    @OptionName("--api-version")
    version: String,
    @OptionName("--api-tos")
    termsOfService: String,
    @OptionName("--api-contact-name")
    contactName: String,
    @OptionName("--api-contact-url")
    contactUrl: String,
    @OptionName("--api-contact-email")
    contactEmail: String,
    @OptionName("--api-license")
    licenseName: String,
    @OptionName("--api-license-url")
    licenseUrl: String,
    @OptionName("--api-host")
    host: String,
    @OptionName("--api-base-path")
    basePath: String
)

/**
 * Provides a factory of plugin options.
 */
object PluginOptions {

  /**
   * A mapping of command-line argument with name of field.
   */
  val OptionMapping = symbolOf[PluginOptions].asClass.primaryConstructor.typeSignature.paramLists.head.map { field =>
    val fieldName = field.name.decodedName.toString
    field.annotations.filter(_.tree.tpe =:= typeOf[OptionName]).headOption
      .map { annotation => (getFirstArgument(annotation.tree), fieldName) }
      .getOrElse(fieldName, fieldName)
  }.toMap

  /**
   * Parses a raw list of options.
   *
   * @param options  a list of options to parse
   */
  def apply(options: Array[Array[String]]): PluginOptions = options.view
    .filter(_.length == 2)
    .filter(option => OptionMapping.contains(option(0)))
    .map(option => (OptionMapping(option(0)), option(1)))
    .toMap.toCaseClass[PluginOptions]

  /**
   * Returns a first string argument for the specified annotation tree.
   */
  private def getFirstArgument(annotation: Tree) = {
    val stringArgument = annotation.children.tail.head.toString
    stringArgument.substring(1, stringArgument.length - 1)
  }

}