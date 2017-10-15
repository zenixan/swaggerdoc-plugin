package org.eu.fuzzy.swagger.model

import java.net.URL

/**
 * Represents a license information for the exposed API.
 *
 * @param name  A name of license for the exposed API.
 * @param url   An URL address of license.
 *
 * @see [[https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#licenseObject License Object]]
 */
case class License(
    name: String,
    url: Option[URL] = None
)