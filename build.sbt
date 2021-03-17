/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License")" + 
    "
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import sbt.Keys._
import sbt._
import uk.gov.hmrc.versioning.SbtGitVersioning.autoImport.majorVersion
import uk.gov.hmrc.versioning.SbtGitVersioning

val scala2_12 = "2.12.12"

// Disable multiple project tests running at the same time: https://stackoverflow.com/questions/11899723/how-to-turn-off-parallel-execution-of-tests-for-multi-project-builds
// TODO: restrict parallelExecution to tests only (the obvious way to do this using Test scope does not seem to work correctly)
parallelExecution in Global := false

val silencerVersion = "1.7.1"

lazy val library = (project in file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
  .settings(PlayCrossCompilation.playCrossCompilationSettings)
  .settings(
    scalaVersion := scala2_12,
    majorVersion := 2,
    name := "http-metrics",
    makePublicallyAvailableOnBintray := true,
    libraryDependencies ++= deps,
    resolvers := Seq(
      Resolver.bintrayRepo("hmrc", "releases")
    ),
    libraryDependencies ++= Seq(
      compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
      "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
    )
  )
  .disablePlugins(sbt.plugins.JUnitXmlReportPlugin)

val deps: Seq[ModuleID] = PlayCrossCompilation.dependencies(
  play26 = (LibDependencies.coreCompilePlay26 ++ LibDependencies.coreTestPlay26),
  play27 = (LibDependencies.coreCompilePlay27 ++ LibDependencies.coreTestPlay27),
  shared = (LibDependencies.coreCompileCommon ++ LibDependencies.coreTestCommon)
)

// Coverage configuration
coverageMinimum := 66
coverageFailOnMinimum := true
coverageExcludedPackages := Seq(
    "<empty>",
    "com.kenshoo.play.metrics",
    "uk.gov.hmrc.BuildInfo",
    "uk.gov.hmrc.play.http.metrics.common"
).mkString(";")