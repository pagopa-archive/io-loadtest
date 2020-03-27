import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

import java.util.Date._
import java.text.SimpleDateFormat._

class uc7 extends Simulation {

  val baseUrl                = System.getProperty("baseUrl")
  val apikeyHeaderKey        = System.getProperty("apikey-header-key")
  val apikeyHeaderValue      = System.getProperty("apikey-header-value")
  val fiscalcodesFile        = System.getProperty("fiscalcodesFile")

  val model                       = System.getProperty("model")
  val steps                       = Integer.getInteger("steps", 5)
  val maxHostConcurrentUsers      = Integer.getInteger("maxHostConcurrentUsers", 10)
  val maxHostIncrementUsersPerSec = Integer.getInteger("maxHostIncrementUsersPerSec", 10)

  val fiscalcodesFeeders = csv(s"$fiscalcodesFile").eager.random

  val httpConf = http
    .baseUrl(baseUrl)
    .header(s"${apikeyHeaderKey}", s"${apikeyHeaderValue}")
    .header("Content-Type", "application/json")

  object GetProfile {

    val getProfile = 
      exec(
        http("Get Profile")
          .get("/api/v1/profiles/${fiscalcode}")
      )
      .pause(1000 milliseconds)
      
  }
  
  val users = scenario("Users").feed(fiscalcodesFeeders).exec(GetProfile.getProfile)

  model match {

    case "open"  => 
    // Open Model
    val incrementUsers = maxHostIncrementUsersPerSec.toFloat/steps
    setUp(
      users.inject(
        incrementUsersPerSec(incrementUsers.toInt) // Double
          .times(steps.toInt) // repetition
          .eachLevelLasting(30 seconds) // time between next increment
          .separatedByRampsLasting(10 seconds)
          .startingFrom(incrementUsers.toInt) // Double
      ).protocols(httpConf)
    )

    case "closed"  => 
    // Closed Model
    val incrementUsers = maxHostConcurrentUsers.toFloat/steps
    setUp(
      users.inject(
        incrementConcurrentUsers(incrementUsers.toInt) // Double
          .times(steps.toInt) // repetition
          .eachLevelLasting(30 seconds) // time between next increment
          .separatedByRampsLasting(10 seconds)
          .startingFrom(incrementUsers.toInt) // Double
      ).protocols(httpConf)
    )

  }

}
