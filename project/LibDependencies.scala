import sbt._

object LibDependencies {
  val playVersion = "3.0.4"

  val compile: Seq[ModuleID] = Seq(
    "io.dropwizard.metrics" % "metrics-graphite" % "4.2.22",
    "org.playframework"    %% "play"             % playVersion,
    "org.playframework"    %% "play-guice"       % playVersion
  )

  val test: Seq[ModuleID] = Seq(
    "org.pegdown"             % "pegdown"                 % "1.6.0",
    "com.github.tomakehurst"  % "wiremock"                % "3.0.0-beta-7",
    "org.playframework"      %% "play-test"               % playVersion,
    "org.scalatestplus.play" %% "scalatestplus-play"      % "7.0.1",
    "com.vladsch.flexmark"    % "flexmark-all"            % "0.64.8"
  ).map(_ % Test)
}
