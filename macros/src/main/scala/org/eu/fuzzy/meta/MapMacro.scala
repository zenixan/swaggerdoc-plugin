package org.eu.fuzzy.meta

import scala.reflect.macros.blackbox.Context

/**
 * Represents a macro bundle to generate a Map conversion methods.
 */
private[meta] class MapMacro(val c: Context) {

  import c.universe._

  /**
   * Implements a conversion of case class to the map.
   * @return
   */
  def toMap(): Expr[Map[String, Any]] = {
    val classValue = c.prefix.tree match {
      case Apply(_, argument :: Nil) => argument
      case _ => c.abort(c.enclosingPosition, "Unable to find a class for the conversion")
    }

    val params = getFields(classValue.tpe).map { field =>
      val name = field.name.decodedName.toString
      q"$name -> $classValue.$field"
    }

    c.Expr[Map[String, Any]](q"Map(..$params)")
  }

  /**
   * Implements a conversion of map to the case class.
   */
  def toCaseClass[T: c.WeakTypeTag](): Expr[T] = {
    val mapValue = c.prefix.tree match {
      case Apply(_, argument :: Nil) => argument
      case _ => c.abort(c.enclosingPosition, "Unable to find a map for the conversion")
    }

    val classType = weakTypeOf[T]
    val params = getFields(classType).zipWithIndex.map { case (field, index) =>
      val name = field.name.decodedName.toString
      val fieldType = field.typeSignature
      q"$mapValue.getOrElse($name, org.eu.fuzzy.meta.Default.value[$fieldType]).asInstanceOf[$fieldType]"
    }

    c.Expr[T](q"new $classType(..$params)")
  }

  /**
   * Returns a list of field accessors for the specified type of case class.
   */
  def getFields(tpe: Type): Seq[TermSymbol] = tpe.decl(termNames.CONSTRUCTOR).asMethod.paramLists.flatten
    .map { parameter => parameter.asTerm }

}
