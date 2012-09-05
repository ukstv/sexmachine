import sbt._
import sbt.Keys._

object SexmachineBuild extends Build {

  lazy val sexmachine = Project(
    id = "sexmachine",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "Sexmachine",
      organization := "me.ukstv",
      version := "0.1",
      scalaVersion := "2.9.2",
      libraryDependencies := Seq(
        "org.specs2" %% "specs2" % "1.9"
      )
      // add other settings here
    )
  )
}
