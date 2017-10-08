package org.eu.fuzzy.swagger.model

import java.net.URL

/**
 * Represents a license information for the exposed API.
 *
 * @param name  A name of license for the exposed API.
 * @param url   An URL address of license.
 *
 * @see [[https://swagger.io/specification/#licenseObject License Object]]
 */
case class License(
    name: String,
    url: Option[URL]
)