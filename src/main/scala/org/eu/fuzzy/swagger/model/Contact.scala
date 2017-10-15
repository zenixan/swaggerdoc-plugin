package org.eu.fuzzy.swagger.model

import java.net.URL

/**
 * Represents a contact information for the exposed API.
 *
 * @param name  A name of the contact person/organization.
 * @param url   An URL address of page with contact information.
 * @param email  An email address of the contact person/organization.
 *
 * @see [[https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#contactObject Contact Object]]
 */
case class Contact(
    name: Option[String] = None,
    url: Option[URL] = None,
    email: Option[String] = None
)
