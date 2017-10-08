package org.eu.fuzzy.swagger.model

/**
 * Represents metadata of API specification.
 *
 * @param title   A title of application.
 * @param description   A short description of application.
 * @param termsOfService  The Terms of Service for the exposed API.
 * @param contact   A contact information for the exposed API.
 * @param license   A license information for the exposed API.
 * @param version   A version of API specification.
 *
 * @see [[http://swagger.io/specification/#infoObject Info Object]]
 */
case class Info(
    title: String,
    description: Option[String],
    termsOfService: Option[String],
    contact: Option[Contact],
    license: Option[License],
    version: String
)
