package org.eu.fuzzy.swagger.javadoc

import com.sun.javadoc._
import com.sun.tools.doclets.standard.Standard
import org.eu.fuzzy.swagger.PluginOptions

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
    PluginOptions(options)
    Standard.validOptions(options, reporter)
  }

  /**
   * Generates a Swagger resource listing.
   */
  def start(root: RootDoc): Boolean = {
    ???
  }


}
