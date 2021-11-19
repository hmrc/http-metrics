import sbt._

object LibDependencies {
  val play26Version = "2.6.25"
  val play27Version = "2.7.9"
  val play28Version = "2.8.7"

  val coreCompileCommon: Seq[ModuleID] = Seq(
    )
    
  val coreCompilePlay26: Seq[ModuleID] = Seq(
    "com.kenshoo"           %% "metrics-play"          % "2.6.6_0.6.2",
    "io.dropwizard.metrics" %  "metrics-graphite"      % "3.2.5",
    "com.typesafe.play"     %% "play"                  % play26Version,
    "com.typesafe.play"     %% "play-guice"            % play26Version
  )

  val coreCompilePlay27: Seq[ModuleID] = Seq(
    "com.kenshoo"           %% "metrics-play"          % "2.7.3_0.8.2",
    "io.dropwizard.metrics" %  "metrics-graphite"      % "4.0.5",
    "com.typesafe.play"     %% "play"                  % play27Version,
    "com.typesafe.play"     %% "play-guice"            % play27Version
    )
    
    val coreCompilePlay28: Seq[ModuleID] = Seq(
    "com.kenshoo"           %% "metrics-play"          % "2.7.3_0.8.2",
    "io.dropwizard.metrics" %  "metrics-graphite"      % "4.1.17",
    "com.typesafe.play"     %% "play"                  % play28Version,
    "com.typesafe.play"     %% "play-guice"            % play28Version
  )

  val coreTestCommon: Seq[ModuleID] = Seq(
    "org.pegdown"             % "pegdown"                   % "1.6.0" % Test,
    "com.github.tomakehurst"  % "wiremock-jre8-standalone"  % "2.27.1" % Test,
    "org.mockito"            %% "mockito-scala-scalatest"   % "1.7.1" % Test
  )

  val coreTestPlay26: Seq[ModuleID] = Seq(
    "com.typesafe.play"      %% "play-test"               % play26Version % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"      % "3.1.3" % Test
  )

  val coreTestPlay27: Seq[ModuleID] = Seq(
    "com.typesafe.play"      %% "play-test"               % play27Version % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"      % "4.0.3" % Test
  )

  val coreTestPlay28: Seq[ModuleID] = Seq(
    "com.typesafe.play"      %% "play-test"               % play28Version % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"      % "5.1.0" % Test,
    "com.vladsch.flexmark"   %  "flexmark-all"            % "0.36.8" % Test
  )
}