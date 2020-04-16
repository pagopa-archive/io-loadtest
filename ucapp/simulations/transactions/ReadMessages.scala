import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

object ReadMessages {

  val readMessages =
    group("ReadMessages") {
      repeat(session => session("readMessageCount").as[Int], "index") {
        tryMax(1) {
          exec { session =>
            val indexRandom     = scala.util.Random.nextInt(session("serviceIdsCount").as[Int] - 1)
            val serviceIdRandom = session("serviceIds").as[Seq[String]].apply(indexRandom)
            session.set("serviceIdRandom", serviceIdRandom).set("indexRandom", indexRandom)
          }.exec(
            http("GET /api/v1/services/{id_service}")
              .get(Configuration.urlappbackend + "/api/v1/services/${serviceIdRandom}")
              .header("Authorization", s"Bearer ${Configuration.sessionToken}")
          )
        }.exitHereIfFailed
          .tryMax(1) {
            exec(
              http("GET /logos/organizations/{organization_fiscal_code}.png")
                .get(Configuration.urlservicesmetadata + "/logos/services/azure-deployc49a.png")
            )
          }
          .exitHereIfFailed
          .pause(20, 30) // will make a random pause of 20-30 seconds
      }
    }
}
