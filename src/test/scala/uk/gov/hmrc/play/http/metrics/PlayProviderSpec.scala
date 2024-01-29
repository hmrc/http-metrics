/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.play.http.metrics

import com.codahale.metrics.Timer.Context
import com.codahale.metrics.{Counter, MetricRegistry}

import uk.gov.hmrc.play.http.metrics.common._

class PlayProviderSpec extends AsyncHmrcSpec {

  trait Setup {

    val mockRegistry = mock[MetricRegistry]

    val api = API("api")

    val failureCounter = mock[Counter]
    when(failureCounter.inc()).thenCallRealMethod()
    when(mockRegistry.counter("api-failed-counter")).thenReturn(failureCounter)

    val successCounter = mock[Counter]
    when(successCounter.inc()).thenCallRealMethod()
    when(mockRegistry.counter("api-success-counter")).thenReturn(successCounter)

    val timer   = mock[com.codahale.metrics.Timer]
    val context = mock[Context]
    when(context.stop()).thenReturn(1L)

    when(mockRegistry.timer("api-timer")).thenReturn(timer)
    when(timer.time()).thenReturn(context)

    val apiMetricsProviderWithMetricImpl = new ApiMetricsProvider(mockRegistry)

  }

  "PlayProvider" when {

    " a metricImpl is returned" should {

      "record failures to the api-failed-counter" in new Setup {
        apiMetricsProviderWithMetricImpl.get().recordFailure(api)

        verify(failureCounter).inc()
        verify(successCounter, never).inc()
      }

      "record successes to the api-counter" in new Setup {
        apiMetricsProviderWithMetricImpl.get().recordSuccess(api)

        verify(successCounter).inc()
        verify(failureCounter, never).inc()
      }

      "record timing to the api-timer" in new Setup {
        val t = apiMetricsProviderWithMetricImpl.get().startTimer(api)
        verify(context, never).stop()

        t.stop()
        verify(context).stop()
      }
    }
  }
}
