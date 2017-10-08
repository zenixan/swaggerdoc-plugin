package org.eu.fuzzy.meta

/**
 * Provides a default value for a type.
 */
class Default[A](val value: A)

/**
 * Provides a factory of default values.
 */
object Default {

  /**
   * Returns a default value for the specified type.
   */
  def value[A](implicit default: Default[A]): A = default.value

  implicit def defaultUnit[A]: Default[Unit] = new Default[Unit](())
  implicit def defaultBoolean[A]: Default[Boolean] = new Default[Boolean](false)
  implicit def defaultChar[A]: Default[Char] = new Default[Char]('\u0000')
  implicit def defaultString[A]: Default[String] = new Default[String]("")

  implicit def defaultByte[A]: Default[Byte] = new Default[Byte](0)
  implicit def defaultShort[A]: Default[Short] = new Default[Short](0)
  implicit def defaultInt[A]: Default[Int] = new Default[Int](0)
  implicit def defaultLong[A]: Default[Long] = new Default[Long](0L)
  implicit def defaultFloat[A]: Default[Float] = new Default[Float](0.0F)
  implicit def defaultDouble[A]: Default[Double] = new Default[Double](0.0)

  implicit def defaultSeq[A]: Default[Seq[A]] = new Default[Seq[A]](Seq())
  implicit def defaultSet[A]: Default[Set[A]] = new Default[Set[A]](Set())
  implicit def defaultMap[A, B]: Default[Map[A, B]] = new Default[Map[A, B]](Map[A, B]())
  implicit def defaultOption[A]: Default[Option[A]] = new Default[Option[A]](None)

}