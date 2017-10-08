package org.eu.fuzzy.swagger.model

/**
 * Represents a root object of API specification.
 *
 * @param swagger  A version of Swagger Specification.
 * @param info  A metadata of API specification.
 * @param host  An optional network address (name or ip) of API server.
 * @param basePath  An optional path to the exposed API. The value must start with a leading slash (/).
 * @param schemes  A list of supported transfer protocols.
 * @param consumes  A list of MIME types which the API can consume.
 * @param produces  A list of MIME types which the API can produce.
 * TODO
 *
 * @see [[http://swagger.io/specification/#swagger-object-14 Swagger Object]]
 */
case class Swagger(
    swagger: String = "2.0",
    info: Info,
    host: Option[String],
    basePath: Option[String],
    schemes: Seq[Protocol],
    consumes: Seq[String],
    produces: Seq[String]
    //test: Option[Info]
)
