/*
 * Copyright 2018 HM Revenue & Customs
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

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

trait Metrics {
  val provider: Provider

  def record[A](api: API)(f: => Future[A])(implicit ec: ExecutionContext): Future[A] = {
    val timer = provider.startTimer(api)

    f.andThen {
      case _ => timer.stop()
    }
    .andThen {
      case Success(_) => provider.recordSuccess(api)
      case Failure(_) => provider.recordFailure(api)
    }
  }
}

object PlayMetrics extends Metrics {
  override lazy val provider = PlayProvider
}

object NoopMetrics extends Metrics {
  override val provider = NoopProvider
  override def record[A](api: API)(f: => Future[A])(implicit ec: ExecutionContext): Future[A] =
    f
}

