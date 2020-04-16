import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

object ReadMessageList {

  val readMessageList =
    group("ReadMessageList") {
      tryMax(1) {
        exec(
          http("GET /api/v1/user-metadata")
            .get(Configuration.urlappbackend + "/api/v1/user-metadata")
            .header("Authorization", s"Bearer ${Configuration.sessionToken}")
        )
      }.exitHereIfFailed
        .tryMax(1) {
          exec(
            http("GET /api/v1/services")
              .get(Configuration.urlappbackend + "/api/v1/services")
              .header("Authorization", s"Bearer ${Configuration.sessionToken}")
              .check(
                jsonPath("$.items..service_id").findAll.saveAs("serviceIds")
              )
              .check(jsonPath("$.page_size").find.saveAs("serviceIdsCount"))
          )
        }
        .exitHereIfFailed
        .repeat(session => session("servicesNewCount").as[Int]) {
          tryMax(1) {
            exec { session =>
              val indexRandom     = scala.util.Random.nextInt(session("serviceIdsCount").as[Int] - 1)
              val serviceIdRandom = session("serviceIds").as[Seq[String]].apply(indexRandom)
              session.set("serviceIdRandom", serviceIdRandom)
            }.exec(
              http("GET /api/v1/services/{id_service}")
                .get(Configuration.urlappbackend + "/api/v1/services/${serviceIdRandom}")
                .header("Authorization", s"Bearer ${Configuration.sessionToken}")
            )
          }.exitHereIfFailed
        }
        .tryMax(1) {
          exec(
            http("GET /services/servicesByScope.json")
              .get(Configuration.urlservicesmetadata + "/services/servicesByScope.json")
          )
        }
        .exitHereIfFailed
        .tryMax(1) {
          exec(
            http("GET /api/v1/messages")
              .get(Configuration.urlappbackend + "/api/v1/messages")
              .header("Authorization", s"Bearer ${Configuration.sessionToken}")
              .check(jsonPath("$.items..id").findAll.saveAs("messageIds"))
              .check(jsonPath("$.page_size").find.saveAs("messageIdsCount"))
          )
        }
        .exitHereIfFailed
        .foreach("${messageIds}", "messageId") {
          tryMax(1) {
            exec(
              http("GET /api/v1/messages/{id_message}")
                .get(Configuration.urlappbackend + "/api/v1/messages/${messageId}")
                .header("Authorization", s"Bearer ${Configuration.sessionToken}")
            )
          }.exitHereIfFailed
        }
        .pause(2, 5)
    }
}
