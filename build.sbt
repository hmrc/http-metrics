/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

import sbt._
import sbt.Keys._
import uk.gov.hmrc.DefaultBuildSettings.targetJvm
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.SbtArtifactory
import uk.gov.hmrc.versioning.SbtGitVersioning.autoImport.majorVersion
import uk.gov.hmrc.versioning.SbtGitVersioning
import play.core.PlayVersion

lazy val library = (project in file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
  .settings(PlayCrossCompilation.playCrossCompilationSettings)
  .settings(
    scalaVersion := "2.11.11",
    name := "http-metrics",
    majorVersion := 1,
    makePublicallyAvailableOnBintray := true,
    targetJvm := "jvm-1.8",
    crossScalaVersions := Seq("2.11.11"),
    libraryDependencies ++= deps,
    resolvers := Seq(
      Resolver.bintrayRepo("hmrc", "releases")
    )
  )

val compileDepsShared: Seq[ModuleID] = Seq(
  "uk.gov.hmrc" %% "play-graphite" % "4.7.0",
  "com.typesafe.play" %% "play" % PlayVersion.current % "test, provided"
)

val compileDepsPlay25: Seq[ModuleID] = Seq(
  "de.threedimensions" %% "metrics-play" % "2.5.13"
)

val compileDepsPlay26: Seq[ModuleID] = Seq(

)

val testDepsShared: Seq[ModuleID] = Seq(
  "com.github.tomakehurst"    % "wiremock" % "2.8.0" % Test,
  "org.mockito"               % "mockito-all" % "1.10.19" % Test
)

val testDepsPlay25: Seq[ModuleID] = Seq(
  "uk.gov.hmrc"             %% "hmrctest"  % "3.9.0-play-25"  % Test,
  "org.scalatestplus.play"  %% "scalatestplus-play" % "2.0.1" % Test,
  "org.mockito"              % "mockito-all" % "1.10.19" % Test
)

val testDepsPlay26: Seq[ModuleID] = Seq(
  "uk.gov.hmrc"             %% "hmrctest"  % "3.9.0-play-26"  % Test,
  "org.scalatestplus.play"  %% "scalatestplus-play" % "3.1.2" % Test,
  "org.mockito"             %% "mockito-scala-scalatest" % "1.7.1" % Test
)

val deps: Seq[ModuleID] = PlayCrossCompilation.dependencies(
  shared = compileDepsShared ++ testDepsShared,
  play25 = compileDepsPlay25 ++ testDepsPlay25,
  play26 = compileDepsPlay26 ++ testDepsPlay26
)

// Coverage configuration
coverageMinimum := 66
coverageFailOnMinimum := true
coverageExcludedPackages := "<empty>;com.kenshoo.play.metrics.*;.*definition.*;prod.*;testOnlyDoNotUseInAppConf.*;app.*;uk.gov.hmrc.BuildInfo"
