package org.eu.fuzzy.json

import org.json4s.native.JsonMethods.parse

package object deserialization {

  /**
   * Provides a JSON deserialization into the Scala objects.
   */
  implicit class JsonReader(json: CharSequence) {

    /**
     * Parses a JSON node from the current string.
     *
     * @return the JSON AST node
     */
    def fromJson(): Value = parse(json.toString)

  }

}
