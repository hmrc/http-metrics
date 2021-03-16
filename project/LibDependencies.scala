import sbt._

object LibDependencies {
  val play26Version = "2.6.25"
  val play27Version = "2.7.9"

  val coreCompileCommon: Seq[ModuleID] = Seq(
    "com.kenshoo"             %% "metrics-play"           % "2.6.6_0.6.2",
    "io.dropwizard.metrics"   %  "metrics-graphite"       % "3.2.5"
  )

  val coreCompilePlay26: Seq[ModuleID] = Seq(
    "com.typesafe.play"     %% "play"                  % play26Version,
    "com.typesafe.play"     %% "play-guice"            % play26Version
  )

  val coreCompilePlay27: Seq[ModuleID] = Seq(
    "com.typesafe.play"     %% "play"                  % play27Version,
    "com.typesafe.play"     %% "play-guice"            % play27Version
  )
  
  val coreTestCommon: Seq[ModuleID] = Seq(
    "org.pegdown"             % "pegdown"                 % "1.6.0" % Test,
    "com.github.tomakehurst"  % "wiremock"                % "2.8.0" % Test,
    "org.mockito"            %% "mockito-scala-scalatest" % "1.7.1" % Test
  )

  val coreTestPlay26: Seq[ModuleID] = Seq(
    "com.typesafe.play"      %% "play-test"               % play26Version % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"      % "3.1.3" % Test
  )
    
  val coreTestPlay27: Seq[ModuleID] = Seq(
    "com.typesafe.play"      %% "play-test"               % play27Version % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"      % "4.0.3" % Test
  )

}