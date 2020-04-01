import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

import java.util.Date._
import java.text.SimpleDateFormat._

class ucapp1 extends Simulation {

  val baseUrl                          = System.getProperty("baseUrl")
  val sessionToken                     = System.getProperty("sessionToken")
  val messageReadCountDistributionFile = System.getProperty("messageReadCountDistributionFile")

  val model                       = System.getProperty("model")
  val steps                       = Integer.getInteger("steps", 5)
  val steadyStateTime             = Integer.getInteger("steadyStateTime", 60)
  val maxHostConcurrentUsers      = Integer.getInteger("maxHostConcurrentUsers", 10)
  val maxHostIncrementUsersPerSec = Integer.getInteger("maxHostIncrementUsersPerSec", 10)
  
  val messageReadCountDistributionFeeders = csv(s"$messageReadCountDistributionFile").eager.random

  val httpConf = http
    .baseUrl(baseUrl)
    .authorizationHeader(s"Bearer $sessionToken")
    .acceptHeader("application/json, */*")

  object OpenIOApp {

    val openIOApp = 

      tryMax(1) {
      exec(
        http("GET /api/v1/profile")
          .get("/api/v1/profile")
      )}.exitHereIfFailed

      .tryMax(1) {
      exec(
        http("GET /api/v1/messages")
          .get("/api/v1/messages")
          .check(jsonPath("$.items..id").findAll.saveAs("messageIds"))
      )}.exitHereIfFailed

      .foreach("${messageIds}", "messageId") {
        tryMax(1) {
        exec(
          http("GET /api/v1/messages/{id_message}")
          .get("/api/v1/messages/${messageId}")
        )}.exitHereIfFailed
      }

      .tryMax(1) {
      exec(
        http("GET /api/v1/services")
          .get("/api/v1/services")
      )}.exitHereIfFailed

      .tryMax(1) {
      exec(
        http("GET /info")
          .get("/info")
      )}.exitHereIfFailed
      
  }

  object ReadMessages {
        
    val readMessages = 
      repeat(session => session("readMessageCount").as[Int], "index") {
        pause(20, 30) // will make a random pause of 20-30 seconds
    }

  }

  val users = scenario("Users").feed(messageReadCountDistributionFeeders).exec(OpenIOApp.openIOApp, ReadMessages.readMessages)

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
          .startingFrom(incrementUsers.toInt), // Double
        constantUsersPerSec(maxHostConcurrentUsers.toInt) during (steadyStateTime.toInt seconds)
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
          .startingFrom(incrementUsers.toInt), // Double
        constantConcurrentUsers(maxHostConcurrentUsers.toInt) during (steadyStateTime.toInt seconds)
      ).protocols(httpConf)
    )

  }

}
