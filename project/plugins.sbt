resolvers += "HMRC-open-artefacts-maven" at "https://open.artefacts.tax.service.gov.uk/maven2"
resolvers += Resolver.url("HMRC-open-artefacts-ivy", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(Resolver.ivyStylePatterns)

addSbtPlugin("uk.gov.hmrc"     % "sbt-auto-build"        % "3.22.0")
addSbtPlugin("org.scoverage"   % "sbt-scoverage"         % "2.1.0")
addSbtPlugin("org.scalameta"   % "sbt-scalafmt"          % "2.5.2")
addSbtPlugin("ch.epfl.scala"   % "sbt-scalafix"          % "0.12.1")
addSbtPlugin("ch.epfl.scala"   % "sbt-bloop"             % "1.6.0")

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
