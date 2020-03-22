## rename this file in vars.sh

# Assuming same user name for all hosts
USER_NAME="user"

# Remote hosts list
HOSTS=(192.168.1.1 192.168.1.2 192.168.1.3)

# Change gatling version
GATLING_VERSION="3.3.1"

# Report Description (no use spaces)
REPORT_DESCRIPTION='2_Host-100_maxHostConcurrentUsers'

# Output report directory
OUTPUT_DIR="$HOME/gatling_reports"

# https://gatling.io/docs/current/general/operations/#ipv4-vs-ipv6
JAVA_OPTS="${JAVA_OPTS} -Djava.net.preferIPv4Stack=true"
JAVA_OPTS="${JAVA_OPTS} -Djava.net.preferIPv6Addresses=false"
