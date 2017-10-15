package org.eu.fuzzy.json.serialization

import java.net.{URI, URL}
import java.util.regex.Pattern

import org.json4s.{JValue, JsonDSL}

import scala.collection.mutable
import scala.reflect.macros.blackbox.Context

/**
 * Represents a macro bundle to generate a JSON serialization methods.
 */
private class JsonWriterMacro(val c: Context) {

  import c.universe._

  /**
   * Represents a pattern of Java accessor method.
   */
  val AccessorPattern = Pattern.compile("(?:get|is)[A-Z].*")

  /**
   * A list of root types for each value.
   */
  val RootTypes = Seq(typeOf[Object], typeOf[Any])

  /**
   * A cached list of implicit conversions from the JSON library.
   */
  val LibraryConversions = typeOf[JsonDSL].members.view
    .collect { case member if member.isMethod && member.isImplicit => member.asMethod }
    .toSeq

  /**
   * Implements a conversion of current value to the JSON string.
   */
  def generatePrettyJson(): Expr[String] =
    c.Expr[String](q"import org.json4s.native.JsonMethods._; pretty(render(${generateJson}))")

  /**
   * Implements a conversion of current value to the compact JSON string.
   */
  def generateCompactJson(): Expr[String] =
    c.Expr[String](q"import org.json4s.native.JsonMethods._; compact(render(${generateJson}))")

  /**
   * Implements a conversion of current value to the JSON node.
   */
  def generateJson(): Expr[JValue] = {
    val value = c.prefix.tree match {
      case Apply(_, argument :: Nil) => argument
      case _ => c.abort(c.enclosingPosition, "Unable to find a value for the serialization")
    }
    val valueType = value.tpe

    val implicitConversions = getAllTypes(valueType).distinct.collect {
      case tpe if (!hasDeclaredConversion(tpe) && !hasLibraryConversion(tpe.erasure)) =>
        val symbol = tpe.typeSymbol
        if (symbol.isClass)
          toJsonConversion(symbol.asClass)
        else
          c.abort(c.enclosingPosition, s"No implicit conversion for ${symbol} => JValue")
    }
    c.Expr[JValue](q"import org.json4s.JsonDSL._; ..$implicitConversions; $value: org.json4s.JValue")
  }

  /**
   * Checks whether the current scope has an implicit conversion to the JSON node.
   */
  def hasDeclaredConversion(tpe: Type) = !c.inferImplicitView(c.prefix.tree, tpe, typeOf[JValue]).isEmpty

  /**
   * Checks whether the specified type has implicit conversion in the JSON library.
   */
  def hasLibraryConversion(tpe: Type) = LibraryConversions.exists {
    method => method.paramLists.collectFirst {
      case parameters if (parameters.size == 1) => tpe <:< parameters.head.typeSignature.erasure
    }.getOrElse(false)
  }

  /**
   * Returns an implicit conversion for the specified class to the JSON object.
   */
  def toJsonConversion(symbol: ClassSymbol): Tree = {
    val classType = symbol.toType
    val jsonFields = getFieldAccessors(classType).map { field =>
      val name = field.name.decodedName.toString
      q"org.json4s.JField($name, x.$field: org.json4s.JValue)"
    }

    val conversion = if (isEnum(symbol)) {
      val objectName = symbol.name.decodedName.toString
      if (symbol.isModuleClass)
        q"org.json4s.JString($objectName)"
      else
        q"org.json4s.JString(x.getClass().getSimpleName())"
    }
    else if (classType =:= typeOf[URL] || classType =:= typeOf[URI]) {
      q"org.json4s.JString(x.toString())"
    }
    else {
      q"org.json4s.JObject(..$jsonFields)"
    }

    val name = prepareConversionName(symbol)
    q"@inline implicit def $name(x: $classType): org.json4s.JValue = $conversion"
  }

  /**
   * Checks whether the specified class represents an enumeration.
   */
  def isEnum(symbol: ClassSymbol) =
    getFieldAccessors(symbol.toType).isEmpty && (symbol.isModuleClass || symbol.isSealed)

  /**
   * Returns a name of implicit conversion for the specified type to the JSON object.
   */
  def prepareConversionName(symbol: TypeSymbol): TermName = TermName(symbol.name.decodedName + "ToJson")

  /**
   * Returns a cached list of fields or field accessors for the specified type.
   */
  lazy val getFieldAccessors: Type => Seq[MethodSymbol] = memoize {
    case tpe => tpe.members.collect {
      case method: MethodSymbol if (method.isGetter || isJavaAccessor(method)) => method
    }.toSeq
  }

  /**
   * Returns a memoized function for the specified function.
   */
  def memoize[K, V](f: K => V): K => V = new mutable.HashMap[K, V]() {
    override def apply(key: K) = getOrElseUpdate(key, f(key))
  }

  /**
   * Checks whether the specified method name follows a standard naming convention of accessor methods.
   */
  def isJavaAccessor(method: MethodSymbol): Boolean =
    !RootTypes.exists(tpe => method.owner.asType.toType =:= tpe) &&
      AccessorPattern.matcher(method.name.decodedName.toString).matches()

  /**
   * Returns a list of all types in the composite type including the specified type.
   */
  def getAllTypes(kind: Type): Seq[Type] = {
    val symbol = kind.typeSymbol
    if (symbol.isModuleClass)
      Seq(kind)
    else if (symbol.isClass && symbol.asClass.isCaseClass)
      getFieldAccessors(kind).flatMap {
        method => resolveTypeParameter(kind, method.returnType).toSeq.flatMap(getAllTypes(_))
      } :+ kind.typeConstructor
    else
      kind.typeArgs.flatMap {
        typeArgument => resolveTypeParameter(kind, typeArgument).toSeq.flatMap(getAllTypes(_))
      } :+ kind.typeConstructor
  }

  /**
   * Substitutes a type parameter by a type from specified list of type arguments.
   */
  def resolveTypeParameter(ownerType: Type, parameter: Type): Option[Type] = {
    val parameterIndex = ownerType.etaExpand.typeParams.indexWhere(_.asType.toType =:= parameter)
    if (parameterIndex >= 0)
      Some(ownerType.typeArgs(parameterIndex))
    else if (!(parameter =:= typeOf[Object]) && (parameter.erasure =:= typeOf[Object]))
      None  // ignore uninferred parameters
    else
      Some(parameter)
  }

}
