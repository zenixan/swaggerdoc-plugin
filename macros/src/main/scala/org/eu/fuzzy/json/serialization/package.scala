package org.eu.fuzzy.json

import scala.language.experimental.macros

package object serialization {

  /**
   * Provides a JSON serialization for the any values.
   */
  implicit class JsonWriter[T](any: T) {

    /**
     * Converts a current value to the JSON node.
     *
     * @return the JSON AST node
     */
    def toJson(): Value = macro JsonWriterMacro.generateJson

    /**
     * Converts a current value to the JSON string.
     *
     * @return the JSON string
     */
    def toPrettyJson(): String = macro JsonWriterMacro.generatePrettyJson

    /**
     * Converts a current value to the compact JSON string.
     *
     * @return the JSON string
     */
    def toCompactJson(): String = macro JsonWriterMacro.generateCompactJson

  }


}
