package org.eu.fuzzy.swagger.model

/**
 * Represents a metadata to a single tag that's used by the Operation Object.
 *
 * @param name  A name of tag.
 * @param description  A short description of tag.
 * @param externalDocs  An additional external documentation for this tag.
 *
 * @see [[https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#tagObject Tag Object]]
 */
case class Tag(
    name: String,
    description: Option[String] = None,
    externalDocs: Option[ExternalDocumentation]
)
