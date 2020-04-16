object Configuration {
  val urlappbackend       = System.getProperty("urlappbackend")
  val urlappbackendstatus = System.getProperty("urlappbackendstatus")
  val urlpagopa           = System.getProperty("urlpagopa")
  val urlservicesmetadata = System.getProperty("urlservicesmetadata")

  val sessionToken                     = System.getProperty("sessionToken")
  val messageReadCountDistributionFile = System.getProperty("messageReadCountDistributionFile")
  val servicesNewCountDistributionFile = System.getProperty("servicesNewCountDistributionFile")

  val model           = System.getProperty("model")
  val steps           = Integer.getInteger("steps", 5)
  val steadyStateTime = Integer.getInteger("steadyStateTime", 60)
  val virtualUsers    = Integer.getInteger("virtualUsers", 10)
}
