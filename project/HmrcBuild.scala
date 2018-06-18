import sbt.Keys._
import sbt._
import uk.gov.hmrc.DefaultBuildSettings._
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning

object HmrcBuild extends Build {

  val appName = "http-metrics"

  private val metricsPlay = "2.5.13"
  private val playGraphite = "3.6.2"

  private val dependencies = Seq(
    "de.threedimensions" %% "metrics-play" % metricsPlay,
    "uk.gov.hmrc" %% "play-graphite" % playGraphite
  )

  private val testScope = "test"
  private val hmrcTest = "3.0.0"
  private val wiremock = "2.8.0"
  private val mockito = "1.10.19"

  private val testDependencies = Seq(
    "uk.gov.hmrc" %% "hmrctest" % hmrcTest % testScope,
    "com.github.tomakehurst" % "wiremock" % wiremock % testScope,
    "org.mockito" % "mockito-all" % mockito % testScope
  )

  val library = Project(appName, file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
    .settings(scalaSettings: _*)
    .settings(defaultSettings(): _*)
    .settings(
      targetJvm := "jvm-1.8",
      scalaVersion := "2.11.11",
      crossScalaVersions := Seq("2.11.11"),
      libraryDependencies ++= dependencies ++ testDependencies,
      resolvers += Resolver.bintrayRepo("hmrc", "releases")
    )
}
