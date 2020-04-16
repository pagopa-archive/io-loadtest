import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

import java.util._
import java.lang._
import java.util.Date._
import java.text.SimpleDateFormat._

class ucapp1 extends Simulation {

  val messageReadCountDistributionFeeders = csv(s"${Configuration.messageReadCountDistributionFile}").eager.random
  val servicesNewCountDistributionFeeders = csv(s"${Configuration.servicesNewCountDistributionFile}").eager.random

  val httpConf = http

  val users = scenario("Users")
    .feed(messageReadCountDistributionFeeders)
    .feed(servicesNewCountDistributionFeeders)
    .exec(
      OpenApp.openApp,
      UnlockApp.unlockApp,
      ReadMessageList.readMessageList,
      ReadMessages.readMessages,
      CloseApp.closeApp
    )

  Configuration.model match {

    case "open" =>
      // Open Model
      val incrementUsers = Configuration.virtualUsers.toFloat / Configuration.steps
      setUp(
        users
          .inject(
            incrementUsersPerSec(incrementUsers.toInt)
              .times(Configuration.steps.toInt) // repetition
              .eachLevelLasting(30 seconds)     // time between next increment
              .separatedByRampsLasting(10 seconds)
              .startingFrom(incrementUsers.toInt),
            constantUsersPerSec(Configuration.virtualUsers.toInt) during (Configuration.steadyStateTime.toInt seconds)
          )
          .protocols(httpConf)
      )

    case "closed" =>
      // Closed Model
      val incrementUsers = Configuration.virtualUsers.toFloat / Configuration.steps
      setUp(
        users
          .inject(
            incrementConcurrentUsers(incrementUsers.toInt)
              .times(Configuration.steps.toInt) // repetition
              .eachLevelLasting(30 seconds)     // time between next increment
              .separatedByRampsLasting(10 seconds)
              .startingFrom(incrementUsers.toInt),
            constantConcurrentUsers(Configuration.virtualUsers.toInt) during (Configuration.steadyStateTime.toInt seconds)
          )
          .protocols(httpConf)
      )

  }

}
