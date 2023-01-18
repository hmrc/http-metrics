import sbt.Keys._
import sbt._

val scala2_12 = "2.12.12"

val silencerVersion = "1.7.1"

lazy val library = (project in file("."))
  .settings(PlayCrossCompilation.playCrossCompilationSettings)
  .settings(
    scalaVersion := scala2_12,
    majorVersion := 2,
    name := "http-metrics",
    makePublicallyAvailableOnBintray := true,
    libraryDependencies ++= deps,
    libraryDependencies ++= Seq(
      compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
      "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
    )
  )
  .disablePlugins(sbt.plugins.JUnitXmlReportPlugin)

val deps: Seq[ModuleID] = PlayCrossCompilation.dependencies(
  play26 = (LibDependencies.coreCompilePlay26 ++ LibDependencies.coreTestPlay26),
  play27 = (LibDependencies.coreCompilePlay27 ++ LibDependencies.coreTestPlay27),
  play28 = (LibDependencies.coreCompilePlay28 ++ LibDependencies.coreTestPlay28),
  shared = (LibDependencies.coreCompileCommon ++ LibDependencies.coreTestCommon)
)

// Coverage configuration
coverageMinimumStmtTotal := 80
coverageFailOnMinimum := true
coverageExcludedPackages := Seq(
    "uk.gov.hmrc.play.http.metrics.common"
).mkString(";")
