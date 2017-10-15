package org.eu.fuzzy.swagger.model

import java.net.URL

/**
 * Represents a reference to external resource for extended documentation.
 *
 * @param description   A short description of the target documentation.
 * @param url   An URL address for the target documentation.
 *
 * @see [[https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#externalDocumentationObject External Documentation Object]]
 */
case class ExternalDocumentation(
    description: Option[String] = None,
    url: URL
)
