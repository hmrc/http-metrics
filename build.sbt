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
val name = "http-metrics"

val scala2_12 = "2.12.12"

// Disable multiple project tests running at the same time: https://stackoverflow.com/questions/11899723/how-to-turn-off-parallel-execution-of-tests-for-multi-project-builds
// TODO: restrict parallelExecution to tests only (the obvious way to do this using Test scope does not seem to work correctly)
parallelExecution in Global := false

val silencerVersion = "1.7.1"

lazy val commonSettings = Seq(
  organization := "uk.gov.hmrc",
  majorVersion := 2,
  scalaVersion := scala2_12,
  makePublicallyAvailableOnBintray := true,
  resolvers := Seq(
    Resolver.bintrayRepo("hmrc", "releases"),
    Resolver.typesafeRepo("releases")
  ),
  scalacOptions ++= Seq("-feature"),
  // libraryDependencies ++= Seq(
  //   compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
  //   "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
  // )
)

lazy val library = (project in file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
  .settings(
    commonSettings,
    publish := {},
    publishLocal := {},
    publishAndDistribute := {},
    crossScalaVersions := Nil,
  )
  .aggregate(
    httpMetrics,
    httpMetricsPlay26,
    httpMetricsPlay27
  )
  .disablePlugins(sbt.plugins.JUnitXmlReportPlugin)

lazy val httpMetrics = Project("http-metrics", file("http-metrics"))
  .enablePlugins(SbtAutoBuildPlugin, SbtArtifactory)
  .settings(
    commonSettings
  )

lazy val httpMetricsPlay26 = Project("http-metrics-play-26", file("http-metrics-play-26"))
  .enablePlugins(SbtAutoBuildPlugin, SbtArtifactory)
  .settings(
    commonSettings,
    sharedSources,
    libraryDependencies ++= 
      LibDependencies.coreCompileCommon ++
      LibDependencies.coreCompilePlay26 ++
      LibDependencies.coreTestCommon ++
      LibDependencies.coreTestPlay26,
    Test / fork := true // akka is not unloaded properly, which can affect other tests
  )
  .dependsOn(httpMetrics)
  
lazy val httpMetricsPlay27 = Project("http-metrics-play-27", file("http-metrics-play-27"))
  .enablePlugins(SbtAutoBuildPlugin, SbtArtifactory)
  .settings(
    commonSettings,
    sharedSources,
    libraryDependencies ++= LibDependencies.coreCompileCommon ++
    LibDependencies.coreCompilePlay27 ++
    LibDependencies.coreTestCommon ++
    LibDependencies.coreTestPlay27,
    Test    / fork := true // akka is not unloaded properly, which can affect other tests
  )
  .dependsOn(httpMetrics)
    
def sharedSources = Seq(
  Compile / unmanagedSourceDirectories   += baseDirectory.value / "../http-metrics-common/src/main/scala",
  Compile / unmanagedResourceDirectories += baseDirectory.value / "../http-metrics-common/src/main/resources",
  Test    / unmanagedSourceDirectories   += baseDirectory.value / "../http-metrics-common/src/test/scala",
  Test    / unmanagedResourceDirectories += baseDirectory.value / "../http-metrics-common/src/test/resources"
)