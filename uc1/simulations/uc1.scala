import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

import java.util.Date._
import java.text.SimpleDateFormat._

class uc1 extends Simulation {

  val baseUrl                = System.getProperty("baseUrl")
  
  val maxHostConcurrentUsers = Integer.getInteger("maxHostConcurrentUsers", 10)
  val incrementFactor        = maxHostConcurrentUsers.toFloat/10
  
  val httpConf = http
    .baseUrl(baseUrl)
    .acceptCharsetHeader("UTF-8")

  object Ping {
    val ping = 
      exec(
        http("IO - appbackend")
          .get("/info")
          .check(status.is(200))
      )
      .pause(1000 milliseconds)
  }

  val users = scenario("Users").exec(Ping.ping)

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
