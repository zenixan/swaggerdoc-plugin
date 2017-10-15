package org.eu.fuzzy.swagger.model

/**
 * Represents a set of operations available on a path.
 *
 * @param get  A definition of a GET operation on a path.
 * @param put  A definition of a PUT operation on a path.
 * @param post  A definition of a POST operation on a path.
 * @param delete  A definition of a DELETE operation on a path.
 * @param options  A definition of a OPTIONS operation on a path.
 * @param head  A definition of a HEAD operation on a path.
 * @param patch  A definition of a PATCH operation on a path.
 * @param parameters  A list of parameters that are applicable for all the operations described under this path.
 *
 * @see [[https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#pathItemObject Path Item Object]]
 */
case class PathItem(
    get: Option[Operation] = None,
    put: Option[Operation] = None,
    post: Option[Operation] = None,
    delete: Option[Operation] = None,
    options: Option[Operation] = None,
    head: Option[Operation] = None,
    patch: Option[Operation] = None,
    parameters: Seq[Parameter] = Seq()
)
