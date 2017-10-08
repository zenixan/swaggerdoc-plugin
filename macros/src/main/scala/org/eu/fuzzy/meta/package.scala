package org.eu.fuzzy

import scala.language.experimental.macros

package object meta {

  /**
   * Provides a case class to the map conversion.
   */
  implicit class MapWriter[T](any: T) {

    /**
     * Converts a case class to the map instance.
     *
     * @return the state of case class
     */
    def toMap(): Map[String, Any] = macro MapMacro.toMap

  }

  /**
   * Provides a map to the case class conversion.
   */
  implicit class MapReader(map: Map[String, Any]) {

    /**
     * Converts a set of fields to the case class.
     *
     * @return the instance of case class
     */
    def toCaseClass[T](): T = macro MapMacro.toCaseClass[T]

  }

}
