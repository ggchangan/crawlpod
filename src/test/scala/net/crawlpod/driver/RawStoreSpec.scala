package net.crawlpod.driver

import org.scalatest.time.Millis
import org.scalatest.time.Minutes
import org.scalatest.time.Span
import net.crawlpod.UnitSpec
import net.crawlpod.core.Queue
import net.crawlpod.core.RawStore
import net.crawlpod.core.CrawlResponse
import net.crawlpod.core.CrawlRequest

/**
 * @author sakthipriyan
 */
class RawStoreSpec extends UnitSpec {

  val raw = RawStore(config.getString("crawlpod.provider.rawstore"))

  "RawStore" when {
    "emptied" should {
      "succeed" in {
        whenReady(raw.empty) {
          s => s should be(())
        }
      }
    }
    "count" should {
      "return 0" in {
        whenReady(raw.count) {
          s => s should be(0)
        }
      }
    }
    val request = CrawlRequest("http://google.com/", "net.crawlpod.extract.Google")
    val response = CrawlResponse(request, 200, Map("Cookie" -> List("Test")), "Response body")
    "put" should {
      "succeed" in {
        whenReady(raw.put(response)) {
          s => s should be(())
        }
      }
    }
    "get" should {
      "return CrawlResponse" in {
        whenReady(raw.get(request)) { r =>
          {
            assert(r.isDefined)
            assert(r.get == response)
          }
        }
      }
    }
    "count" should {
      "return 1" in {
        whenReady(raw.count) {
          s => s should be(1)
        }
      }
    }
  }
}