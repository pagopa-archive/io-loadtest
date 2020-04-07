## rename this file in vars.sh

#Change to your simulation path 
GATLING_WORK_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

#Change to your simulation class name
SIMULATION_NAME="ucapp1"

$(echo ${JAVA_OPTS} | grep -q 'DbaseUrl=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DbaseUrl=https://app-backend.io.italia.it"; fi

# Authentication headers
$(echo ${JAVA_OPTS} | grep -q 'DsessionToken=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DsessionToken=XXX"; fi

# Message file
$(echo ${JAVA_OPTS} | grep -q 'DmessageReadCountDistributionFile=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DmessageReadCountDistributionFile=messageReadCountDistribution.csv"; fi

# model can be open or closed
$(echo ${JAVA_OPTS} | grep -q 'Dmodel=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Dmodel=closed"; fi

$(echo ${JAVA_OPTS} | grep -q 'DsteadyStateTime=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DsteadyStateTime=600"; fi

$(echo ${JAVA_OPTS} | grep -q 'Dsteps=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Dsteps=5"; fi

# for closed model
$(echo ${JAVA_OPTS} | grep -q 'DmaxHostConcurrentUsers=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DmaxHostConcurrentUsers=60"; fi
# for open model
$(echo ${JAVA_OPTS} | grep -q 'DmaxHostIncrementUsersPerSec=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DmaxHostIncrementUsersPerSec=60"; fi
