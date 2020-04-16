import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

object UnlockApp {

  val unlockApp =
    group("UnlockApp") {
      pause(500.milliseconds, 1000.milliseconds) // will make a random pause of 0.5-1 seconds
    }
}
