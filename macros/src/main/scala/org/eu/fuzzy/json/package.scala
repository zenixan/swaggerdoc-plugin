package org.eu.fuzzy

import org.json4s.JValue

/**
 * Provides a serialization/deserialization of JSON objects.
 *
 * @see [[https://en.wikipedia.org/wiki/JSON JSON]]
 */
package object json {

  /**
   * Represents a JSON value, e.g.: number, string, object, etc.
   */
  type Value = JValue

}