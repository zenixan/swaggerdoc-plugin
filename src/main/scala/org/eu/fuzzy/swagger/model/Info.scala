package org.eu.fuzzy.swagger.model

/**
 * Represents a metadata of API specification.
 *
 * @param title   A title of application.
 * @param description   A short description of application.
 * @param termsOfService  The Terms of Service for the exposed API.
 * @param contact   A contact information for the exposed API.
 * @param license   A license information for the exposed API.
 * @param version   A version of API specification.
 *
 * @see [[https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#infoObject Info Object]]
 */
case class Info(
    title: String,
    description: Option[String] = None,
    termsOfService: Option[String] = None,
    contact: Option[Contact] = None,
    license: Option[License] = None,
    version: String
)
