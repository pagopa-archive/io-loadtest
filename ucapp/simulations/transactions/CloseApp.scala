import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

object CloseApp {

  val closeApp =
    group("CloseApp") {
      pause(200.milliseconds, 500.milliseconds) // will make a random pause of 0.5-1 seconds
    }
}
