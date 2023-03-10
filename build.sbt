import sbt.Keys._
import sbt._

lazy val scala212 = "2.12.16"
lazy val scala213 = "2.13.8"
lazy val supportedScalaVersions = List(scala212, scala213)

val silencerVersion = "1.7.12"

ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"

inThisBuild(
  List(
    scalaVersion := scala213,
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision
  )
)

lazy val library = (project in file("."))
  .settings(
    crossScalaVersions := supportedScalaVersions,
  )
  .settings(
    scalaVersion := scala213,
    majorVersion := 2,
    name := "http-metrics",
    isPublicArtefact := true,
    libraryDependencies ++= LibDependencies.compile ++ LibDependencies.test,
    libraryDependencies ++= Seq(
      compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
      "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
    )
  )
  .disablePlugins(sbt.plugins.JUnitXmlReportPlugin)

// Coverage configuration
coverageMinimumStmtTotal := 80
coverageFailOnMinimum := true
coverageExcludedPackages := Seq(
    "uk.gov.hmrc.play.http.metrics.common"
).mkString(";")
