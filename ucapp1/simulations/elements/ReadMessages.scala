import io.gatling.core.Predef._
import io.gatling.http.Predef._

object ReadMessages {

  val readMessages =
    repeat(session => session("readMessageCount").as[Int]) {
      tryMax(2) {
        exec { session =>
          val indexRandom     = scala.util.Random.nextInt(session("serviceIdsCount").as[Int] - 1)
          val serviceIdRandom = session("serviceIds").as[Seq[String]].apply(indexRandom)
          session.set("serviceIdRandom", serviceIdRandom).set("indexRandom", indexRandom)
        }.exec(
          http("GET /api/v1/services/{id_service}")
            .get(Configuration.urlappbackend + "/api/v1/services/${serviceIdRandom}")
            .header("Authorization", s"Bearer ${Configuration.sessionToken}")
            .check(
              jsonPath("$.organization_fiscal_code").exists
            )
            .check(
              jsonPath("$.organization_fiscal_code").find
                .saveAs("organization_fiscal_code")
            )
        )
      }.exitHereIfFailed
        .exec { session =>
          val organization_fiscal_code = session("organization_fiscal_code")
            .as[String]
            .replaceFirst("^0*", "")
          session.set("organization_fiscal_code", organization_fiscal_code)
        }
        .tryMax(1) {
          exec(
            http("GET /logos/organizations/{organization_fiscal_code}")
              .get(Configuration.urlservicesmetadata + "/logos/organizations/${organization_fiscal_code}.png")
          )
        }
        .exitHereIfFailed
        .pause(20, 30) // will make a random pause of 20-30 seconds
    }

}
