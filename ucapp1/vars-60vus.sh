## rename this file in vars.sh

#Change to your simulation path 
GATLING_WORK_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

#Change to your simulation class name
SIMULATION_NAME="ucapp1"

JAVA_OPTS="${JAVA_OPTS} -DbaseUrl=https://app-backend.io.italia.it"
JAVA_OPTS="${JAVA_OPTS} -DsessionToken=XXX"
JAVA_OPTS="${JAVA_OPTS} -DmessageReadCountDistributionFile=messageReadCountDistribution.csv"

JAVA_OPTS="${JAVA_OPTS} -Dmodel=closed"
JAVA_OPTS="${JAVA_OPTS} -DsteadyStateTime=600"
JAVA_OPTS="${JAVA_OPTS} -Dsteps=5"
# for closed model
JAVA_OPTS="${JAVA_OPTS} -DmaxHostConcurrentUsers=60"
# for open model
JAVA_OPTS="${JAVA_OPTS} -DmaxHostIncrementUsersPerSec=60"
