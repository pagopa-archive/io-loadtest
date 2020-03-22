//********************************************************************
//* CHANGELOG
//*
//* 03/05/2019 - Jason Rizio - Updated example for Gatling 3.0
//********************************************************************

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

import java.util.Date._
import java.text.SimpleDateFormat._

// We upload this simulation separately to the user-files.zip
// in this advanced example so that Flood IO can parse the
// simulation name to execute.
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
          .get("/ping")
          .check(status.is(200))
      )
      .pause(1000 milliseconds, 3000 milliseconds)
  }

  val users = scenario("Users").exec(Ping.ping)

  setUp(
    users.inject(
      incrementConcurrentUsers(incrementFactor.toInt) // Double
        .times(10) // repetition
        .eachLevelLasting(30 seconds) // time between next increment
        .startingFrom(incrementFactor.toInt) // Double
    ).protocols(httpConf)
  )

}
