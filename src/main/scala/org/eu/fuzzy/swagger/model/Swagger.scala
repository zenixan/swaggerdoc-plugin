package org.eu.fuzzy.swagger.model

/**
 * Represents a root object of API specification.
 *
 * @param swagger  A version of Swagger Specification.
 * @param info  A metadata of API specification.
 * @param host  An optional network address (name or ip) of API server.
 * @param basePath  An optional path to the exposed API. The value must begin with a slash.
 * @param schemes  A list of supported transfer protocols.
 * @param consumes  A list of MIME types which the API can consume.
 * @param produces  A list of MIME types which the API can produce.
 * @param paths  A set of available paths and operations for the API. The key of map must begin with a slash.
 * TODO
 * @param tags  A list of tags which are used by the specification.
 * @param externalDocs  An additional external documentation.
 *
 * @see [[https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#swagger-object Swagger Object]]
 */
case class Swagger(
    swagger: String = "2.0",
    info: Info,
    host: Option[String] = None,
    basePath: Option[String] = None,
    schemes: Seq[Protocol] = Seq(),
    consumes: Seq[String] = Seq(),
    produces: Seq[String] = Seq(),
    paths: Map[String, PathItem],
    tags: Seq[Tag] = Seq(),
    externalDocs: Option[ExternalDocumentation] = None
)
