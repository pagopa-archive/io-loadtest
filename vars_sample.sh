## rename this file in vars.sh

# Assuming same user name for all hosts
if [ -z ${USER_NAME} ]; then USER_NAME="user"; fi

# Remote hosts list
HOSTS=
#HOSTS=(192.168.1.1 192.168.1.2 192.168.1.3)

# Change gatling version
if [ -z ${GATLING_VERSION} ]; then GATLING_VERSION="3.3.1"; fi

# Report Description (no use spaces)
if [ -z ${REPORT_DESCRIPTION} ]; then REPORT_DESCRIPTION="1_Host"; fi

# Output report directory
if [ -z ${OUTPUT_DIR} ]; then OUTPUT_DIR="$HOME/gatling_reports"; fi

# https://gatling.io/docs/current/general/operations/#ipv4-vs-ipv6
$(echo ${JAVA_OPTS} | grep -q 'Djava.net.preferIPv4Stack=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Djava.net.preferIPv4Stack=true"; fi
$(echo ${JAVA_OPTS} | grep -q 'Djava.net.preferIPv6Addresses=')
if [ $? -ne 0 ]; then JAVA_OPTS="${JAVA_OPTS} -Djava.net.preferIPv6Addresses=false"; fi
