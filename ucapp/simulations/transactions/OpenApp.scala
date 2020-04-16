import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

object OpenApp {

  val openApp =
    group("OpenApp") {
      pause(500.milliseconds, 1000.milliseconds) // will make a random pause of 0.5-1 seconds
        .tryMax(1) {
          exec(
            http("GET /info")
              .get(Configuration.urlappbackend + "/info")
          )
        }
        .exitHereIfFailed
        .tryMax(1) {
          exec(
            http("GET /status/backend.json")
              .get(Configuration.urlappbackendstatus + "/status/backend.json")
          )
        }
        .exitHereIfFailed
        .tryMax(1) {
          exec(
            http("GET /api/v1/profile")
              .get(Configuration.urlappbackend + "/api/v1/profile")
              .header("Authorization", s"Bearer ${Configuration.sessionToken}")
          )
        }
        .exitHereIfFailed
    }
}
