## rename this file in vars.sh

#Change to your simulation path 
GATLING_WORK_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

#Change to your simulation class name
SIMULATION_NAME="uc3"

JAVA_OPTS="${JAVA_OPTS} -DbaseUrl=https://api.io.italia.it"
JAVA_OPTS="${JAVA_OPTS} -DmaxHostConcurrentUsers=800"
JAVA_OPTS="${JAVA_OPTS} -Dapikey-header-key=Ocp-Apim-Subscription-Key"
JAVA_OPTS="${JAVA_OPTS} -Dapikey-header-value=XXX"
