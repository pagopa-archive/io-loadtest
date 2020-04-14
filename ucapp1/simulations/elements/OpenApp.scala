import io.gatling.core.Predef._
import io.gatling.http.Predef._

object OpenApp {

  val openApp =
    pause(1, 2) // will make a random pause of 1-2 seconds
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
