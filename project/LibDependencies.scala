import sbt._

object LibDependencies {
  val play28Version = "2.8.18"

  val compile: Seq[ModuleID] = Seq(
    "com.kenshoo"            %% "metrics-play"              % "2.7.3_0.8.2",
    "io.dropwizard.metrics"   % "metrics-graphite"          % "4.1.17",
    "com.typesafe.play"      %% "play"                      % play28Version,
    "com.typesafe.play"      %% "play-guice"                % play28Version
  )

  val test: Seq[ModuleID] = Seq(
    "org.pegdown"             % "pegdown"                   % "1.6.0",
    "com.github.tomakehurst"  % "wiremock-jre8-standalone"  % "2.27.1",
    "org.mockito"            %% "mockito-scala-scalatest"   % "1.7.1",
    "com.typesafe.play"      %% "play-test"                 % play28Version,
    "org.scalatestplus.play" %% "scalatestplus-play"        % "5.1.0",
    "com.vladsch.flexmark"    % "flexmark-all"              % "0.36.8"
  ).map(_ % Test)
}