## rename this file in vars.sh

#Change to your simulation path 
GATLING_WORK_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

#Change to your simulation class name
SIMULATION_NAME="uc7"

$(echo ${JAVA_OPTS} | grep -q 'DbaseUrl=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DbaseUrl=https://api.io.italia.it"; fi

# Authentication headers
$(echo ${JAVA_OPTS} | grep -q 'Dapikey-header-key=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Dapikey-header-key=Ocp-Apim-Subscription-Key"; fi
$(echo ${JAVA_OPTS} | grep -q 'Dapikey-header-value=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Dapikey-header-value=XXX"; fi

# Fiscalcode file
$(echo ${JAVA_OPTS} | grep -q 'DfiscalcodesFile=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DfiscalcodesFile=fiscalcodes.csv"; fi

# model can be open or closed
$(echo ${JAVA_OPTS} | grep -q 'Dmodel=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Dmodel=open"; fi

$(echo ${JAVA_OPTS} | grep -q 'Dsteps=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Dsteps=5"; fi

# for closed model
$(echo ${JAVA_OPTS} | grep -q 'DmaxHostConcurrentUsers=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DmaxHostConcurrentUsers=900"; fi
# for open model
$(echo ${JAVA_OPTS} | grep -q 'DmaxHostIncrementUsersPerSec=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DmaxHostIncrementUsersPerSec=900"; fi
