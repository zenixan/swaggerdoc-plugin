import java.io.FileNotFoundException

import sbt.Keys._
import sbt._

import scala.util.Try

/**
 * This plugin adds the JDK component ''tools.jar'' as a system dependency.
 *
 * @note SPDX-License-Identifier: MIT
 */
object JavaToolsPlugin extends AutoPlugin {

  override def requires = sbt.plugins.JvmPlugin
  override def trigger = allRequirements

  /**
   * A set of default settings of plugin.
   */
  lazy val baseSettings: Seq[Def.Setting[_]] = Seq(
    unmanagedJars += Attributed.blank(getFullPath)
  )

  override def projectSettings: Seq[Def.Setting[_]] = inConfig(Compile)(baseSettings)

  /**
   * Returns a full path to the JDK file tools.jar
   */
  private def getFullPath: File = {
    val paths = List(
      sys.env.get("JAVA_HOME").map { _.replaceAll("^/cygdrive/([a-z])/", "$1:/") },   // Apply fix for Cygwin
      sys.props.get("java.home").map { file(_).getParent },
      sys.props.get("java.home"),
      Try("/usr/libexec/java_home".!!).toOption,  // Mac OSX 10.5 or later
      sys.env.get("JDK_HOME")
    )

    paths.flatten
      .map { file(_) / "lib" / "tools.jar" }
      .find { _.exists  }
      .getOrElse {
        throw new FileNotFoundException(
          "Unable to find the JDK file tools.jar. Please set enviroment variable JAVA_HOME or JDK_HOME."
        )
      }
  }

}
