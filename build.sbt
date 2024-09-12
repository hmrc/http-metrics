import sbt.Keys._
import sbt._

lazy val appName = "http-metrics"

Global / bloopAggregateSourceDependencies := true
Global / bloopExportJarClassifiers := Some(Set("sources"))

ThisBuild / scalaVersion := "3.4.2"
ThisBuild / majorVersion := 3
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

lazy val library = Project(appName, file("."))
  .settings(
    isPublicArtefact := true,
    libraryDependencies ++= LibDependencies.compile ++ LibDependencies.test
  )
  .disablePlugins(sbt.plugins.JUnitXmlReportPlugin)

// Coverage configuration
coverageMinimumStmtTotal := 79
coverageFailOnMinimum    := true
coverageExcludedPackages := Seq(
  "uk.gov.hmrc.play.http.metrics.common"
).mkString(";")

commands ++= Seq(
  Command.command("run-all-tests") { state => "test" :: state },
  Command.command("clean-and-test") { state => "clean" :: "compile" :: "run-all-tests" :: state },
  Command.command("pre-commit") { state => "clean" :: "scalafmtAll" :: "scalafixAll" :: "coverage" :: "run-all-tests" :: "coverageOff" :: "coverageAggregate" :: state }
)
