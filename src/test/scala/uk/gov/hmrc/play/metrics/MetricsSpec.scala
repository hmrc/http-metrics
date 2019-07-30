/*
 * Copyright 2019 HM Revenue & Customs
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

import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import uk.gov.hmrc.play.test.UnitSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future,Promise}

class MetricsSpec extends UnitSpec with MockitoSugar {

  trait Setup {
    val metrics = new Metrics {
      val provider = mock[Provider]
    }
    val timer = mock[Timer]
    val api = API("upstream-api")

    when(metrics.provider.startTimer(api)).thenReturn(timer)
  }

  "Metrics" should {

    "return an equivalent future to the one provided" in new Setup {
      val successfulFuture =
        metrics.record(api)(Future.successful(1))

      await(successfulFuture) shouldEqual 1

      val error = new Throwable("error")
      val failedFuture =
        metrics.record(api)(Future.failed(error))

      await(failedFuture.failed) shouldEqual error
    }

    "start a timer immediately" in new Setup {
      val promise = Promise[Int]
      metrics.record(api)(promise.future)

      verify(metrics.provider).startTimer(api)
    }

    "stop the timer when the provided future succeeds" in new Setup {
      val promise = Promise[Int]
      val result = metrics.record(api)(promise.future)
      verify(timer, never).stop

      promise.success(1)
      await(result)

      verify(timer).stop
    }

    "stop the timer when the provided future failed" in new Setup {
      val promise = Promise[Int]
      val result = metrics.record(api)(promise.future).failed
      verify(timer, never).stop

      promise.failure(new Throwable("error"))
      await(result)

      verify(timer).stop
    }

    "record a failure when the provided future fails" in new Setup {
      val promise = Promise[Int]
      val result = metrics.record(api)(promise.future).failed
      promise.failure(new Throwable("error"))

      await(result)

      verify(metrics.provider).recordFailure(api)
      verify(metrics.provider, never).recordSuccess(api)
    }

    "record a success when the provided future succeeds" in new Setup {
      val promise = Promise[Int]
      val result = metrics.record(api)(promise.future)
      promise.success(1)

      await(result)

      verify(metrics.provider).recordSuccess(api)
      verify(metrics.provider, never).recordFailure(api)
    }

    "not record either a success or failure if the future is still pending" in new Setup {
      val promise = Promise[Int]
      metrics.record(api)(promise.future)

      verify(metrics.provider, never).recordFailure(api)
      verify(metrics.provider, never).recordSuccess(api)
    }
  }
}