import sbt.Keys._
import sbt._

scalaVersion := "2.13.12"

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
ThisBuild / semanticdbEnabled                                    := true
ThisBuild / semanticdbVersion                                    := scalafixSemanticdb.revision

lazy val library = (project in file("."))
  .settings(
    majorVersion     := 2,
    name             := "http-metrics",
    isPublicArtefact := true,
    libraryDependencies ++= LibDependencies.compile ++ LibDependencies.test
  )
  .disablePlugins(sbt.plugins.JUnitXmlReportPlugin)

// Coverage configuration
coverageMinimumStmtTotal := 80
coverageFailOnMinimum    := true
coverageExcludedPackages := Seq(
  "uk.gov.hmrc.play.http.metrics.common"
).mkString(";")

commands ++= Seq(
  Command.command("run-all-tests") { state => "test" :: state },
  Command.command("clean-and-test") { state => "clean" :: "compile" :: "run-all-tests" :: state },
  Command.command("pre-commit") { state => "clean" :: "scalafmtAll" :: "scalafixAll" :: "coverage" :: "run-all-tests" :: "coverageOff" :: "coverageAggregate" :: state }
)
