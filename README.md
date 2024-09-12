# HTTP Metrics

Metrics for connectors, to provide response times, error & success counts for downstream services.  Leverages the existing MDTP patterns to make it as easy as possible to add metrics to your services.   

The library is designed as a dependency for API Platform services and is subject to significant change without notice.

## Usage 

```scala
import uk.gov.hmrc.play.http.metrics.{Metrics,PlayMetrics}

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

---
## Disabling metrics for Testing

Instead of `PlayMetrics`, use `NoopMetrics` to disable the metrics gathering, keeping the rest of the business logic intact. 

Alternatively when using Guice Application Builder you can set the metrics.jvm config to false.
```
    new GuiceApplicationBuilder()
    .configure(
        "metrics.jvm" -> false
    )
```

---

## Unit tests
```
sbt test
```

---

## Installing

In your SBT build add:

``` scala
libraryDependencies += "uk.gov.hmrc" %% "http-metrics" % "[INSERT-VERSION]"
```

## Versioning

Versions 3.0.0 and above require Scala 3 and support Play 3.

Versions 2.x.x support Scala 2

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")
