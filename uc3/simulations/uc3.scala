import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

import java.util.Date._
import java.text.SimpleDateFormat._

class uc3 extends Simulation {

  val baseUrl                = System.getProperty("baseUrl")
  val apikeyHeaderKey        = System.getProperty("apikey-header-key")
  val apikeyHeaderValue      = System.getProperty("apikey-header-value")

  val maxHostConcurrentUsers = Integer.getInteger("maxHostConcurrentUsers", 10)
  val incrementFactor        = maxHostConcurrentUsers.toFloat/10
  
  val httpConf = http
    .baseUrl(baseUrl)
    .header(s"${apikeyHeaderKey}", s"${apikeyHeaderValue}")
    .header("Content-Type", "application/json")

  object EchoRequest {

    val echoRequest = 
      exec(
        http("Echo Request")
          .get("/test/echo-request")
          .check(jsonPath("$.method").is("GET"))
      )

  }
  
  val users = scenario("Users").exec(EchoRequest.echoRequest)

  setUp(
    users.inject(
      incrementUsersPerSec(incrementFactor.toInt)
        .times(5)
        .eachLevelLasting(30 seconds)
        .separatedByRampsLasting(10 seconds)
        .startingFrom(incrementFactor.toInt)
    ).protocols(httpConf)
  )

}
