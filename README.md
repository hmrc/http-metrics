# http-metrics

Metrics for connectors, to provide response times, error & success counts for downstream services.  Leverages the existing MDTP patterns to make it as easy as possible to add metrics to your services.   

## Installation

```
    "uk.gov.hmrc" %% "http-metrics" % "x.x.x"
```

## Usage 

```scala
import uk.gov.hmrc.play.http.metrics.{Metrics,PlayMetrics,API}

trait HelloWorldConnector { 
  val metrics: Metrics
    
  val api = API("hello-world") 
    
  def hello(planet: String) = metrics.record(api) {
    WS.url(s"https://my-site.com/hello/$planet").get()
  }

}

object HelloWorldConnector extends HelloWorldConnector {
    val metrics = PlayMetrics 
}
```


## Disabling metrics for Testing

Instead of `PlayMetrics`, use `NoopMetrics` to disable the metrics gathering, keeping the rest of the business logic intact. 

---

## Unit tests
```
sbt test
```

---

## Installing
 
Include the following dependency in your SBT build
 
``` scala
resolvers += Resolver.bintrayRepo("hmrc", "releases")
 
libraryDependencies += "uk.gov.hmrc" %% "http-metrics_2.11" % "[INSERT-VERSION]"
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")
