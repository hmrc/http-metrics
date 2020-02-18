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

import sbt.Keys._
import sbt._
import uk.gov.hmrc.DefaultBuildSettings.targetJvm
import uk.gov.hmrc.versioning.SbtGitVersioning
import uk.gov.hmrc.versioning.SbtGitVersioning.autoImport.majorVersion
import uk.gov.hmrc.{SbtArtifactory, SbtAutoBuildPlugin}

lazy val deps: Seq[ModuleID] = compile ++ test


lazy val library = (project in file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
  .settings(
    scalaVersion := "2.12.10",
    name := "http-metrics",
    majorVersion := 1,
    makePublicallyAvailableOnBintray := true,
    targetJvm := "jvm-1.8",
    libraryDependencies ++= deps,
    resolvers := Seq(
      Resolver.bintrayRepo("hmrc", "releases")
    )
  )

val compile: Seq[ModuleID] = Seq(
  "uk.gov.hmrc" %% "bootstrap-play-26" % "1.3.0"
)

val test: Seq[ModuleID] = Seq(
  "com.github.tomakehurst"    % "wiremock" % "2.8.0" % Test,
  "org.mockito"               % "mockito-all" % "1.10.19" % Test,
  "org.scalatestplus.play"  %% "scalatestplus-play" % "3.1.2" % Test,
  "uk.gov.hmrc"             %% "hmrctest"  % "3.9.0-play-26"  % Test
)

// Coverage configuration
coverageMinimum := 66
coverageFailOnMinimum := true
coverageExcludedPackages := "<empty>;com.kenshoo.play.metrics.*;.*definition.*;prod.*;testOnlyDoNotUseInAppConf.*;app.*;uk.gov.hmrc.BuildInfo"
