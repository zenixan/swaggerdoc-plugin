package org.eu.fuzzy.swagger.javadoc.parser

import javax.ws.rs.Path
import com.sun.javadoc.ClassDoc

/**
 * Represents a JAX-RS resource.
 *
 * @see [[https://en.wikipedia.org/wiki/Java_API_for_RESTful_Web_Services JAX-RS]]
 */
class JaxRsResource private (doc: ClassDoc) {

  def getPaths(): Unit = {
    doc.methods.map { ??? }
    ???
  }

}

/**
 * Provides a factory JAX-RS resources.
 */
object JaxRsResource {

  /**
   * Parses a JAX-RS annotations.
   *
   * @param doc  a reference to Java class
   */
  def apply(doc: ClassDoc): Option[JaxRsResource] = doc.annotations()
    .filter(annotation => annotation.annotationType.qualifiedTypeName == classOf[Path].getName)
    .map(_ => new JaxRsResource(doc))
    .headOption

}