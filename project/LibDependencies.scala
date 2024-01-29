import sbt._

object LibDependencies {
  val playVersion = "3.0.1"

  val compile: Seq[ModuleID] = Seq(
    "io.dropwizard.metrics" % "metrics-graphite" % "4.2.22",
    "org.playframework"    %% "play"             % playVersion,
    "org.playframework"    %% "play-guice"       % playVersion
  )

  val test: Seq[ModuleID] = Seq(
    "org.pegdown"             % "pegdown"                  % "1.6.0",
    "com.github.tomakehurst"  % "wiremock-jre8-standalone" % "2.27.1",
    "org.mockito"            %% "mockito-scala-scalatest"  % "1.7.1",
    "org.playframework"      %% "play-test"                % playVersion,
    "org.scalatestplus.play" %% "scalatestplus-play"       % "5.1.0",
    "com.vladsch.flexmark"    % "flexmark-all"             % "0.36.8"
  ).map(_ % Test)
}
