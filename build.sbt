import sbt.Keys._
import sbt._

val scala2_12 = "2.12.15"

val silencerVersion = "1.7.6"

ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"

inThisBuild(
  List(
    scalaVersion := scala2_12,
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision
  )
)

lazy val library = (project in file("."))
  .settings(
    scalaVersion := scala2_12,
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
