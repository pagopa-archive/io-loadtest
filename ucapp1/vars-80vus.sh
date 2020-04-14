## rename this file in vars.sh

#Change to your simulation path 
GATLING_WORK_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

#Change to your simulation class name
SIMULATION_NAME="ucapp1"

$(echo ${JAVA_OPTS} | grep -q 'Durlappbackend=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Durlappbackend=https://app-backend.io.italia.it"; fi

$(echo ${JAVA_OPTS} | grep -q 'Durlappbackendstatus=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Durlappbackendstatus=https://iopstcdnassets.z6.web.core.windows.net"; fi

$(echo ${JAVA_OPTS} | grep -q 'Durlpagopa=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Durlpagopa=https://wisp2.pagopa.gov.it/pp-restapi-CD"; fi

$(echo ${JAVA_OPTS} | grep -q 'Durlservicesmetadata=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Durlservicesmetadata=https://raw.githubusercontent.com/pagopa/io-services-metadata/master"; fi

# Authentication headers
$(echo ${JAVA_OPTS} | grep -q 'DsessionToken=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DsessionToken=XXX"; fi

# Message file
$(echo ${JAVA_OPTS} | grep -q 'DmessageReadCountDistributionFile=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DmessageReadCountDistributionFile=messageReadCountDistribution.csv"; fi

# Service file
$(echo ${JAVA_OPTS} | grep -q 'DservicesNewCountDistributionFile=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DservicesNewCountDistributionFile=servicesNewCountDistribution.csv"; fi

# model can be open or closed
$(echo ${JAVA_OPTS} | grep -q 'Dmodel=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Dmodel=closed"; fi

$(echo ${JAVA_OPTS} | grep -q 'DsteadyStateTime=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DsteadyStateTime=600"; fi

$(echo ${JAVA_OPTS} | grep -q 'Dsteps=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Dsteps=5"; fi

$(echo ${JAVA_OPTS} | grep -q 'DvirtualUsers=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -DvirtualUsers=80"; fi
