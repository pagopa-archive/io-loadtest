#!/bin/bash
##################################################################################################################
# Gatling scale out/cluster run script:
# Before running this script some assumptions are made:
# 1) Public keys were exchange inorder to ssh with no password promot (ssh-copy-id on all remotes)
# 2) Check  read/write permissions on all folders declared in this script.
# 3) Gatling installation (GATLING_HOME variable) is the same on all hosts
# 4) Assuming all hosts has the same user name (if not change in script)
##################################################################################################################

# gating version
GATLING_VARS=$1

if [ -z ${1+x} ]; then 
    echo "ERROR - GATLING_VARS is unset"
    exit 1
else
    echo "GATLING_VARS is set to '$GATLING_VARS'"
fi

WORKDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

source $WORKDIR/vars.sh
source $WORKDIR/$GATLING_VARS

export JAVA_OPTS
#echo "JAVA_OPTS is set to '$JAVA_OPTS'"

# Assuming all Gatling installation in same path (with write permissions)
GATLING_HOME=/opt/gatling_$GATLING_VERSION
GATLING_USERFILES_DIR=$GATLING_HOME/user-files
GATLING_RUNNER=$GATLING_HOME/bin/gatling.sh

# No need to change this
GATHER_REPORTS_DIR=$GATLING_HOME/reports/
GATLING_REPORT_DIR=$GATLING_HOME/results/

echo "Starting Gatling cluster run for simulation: $SIMULATION_NAME"

echo "Cleaning previous runs from localhost"
rm -rf $GATHER_REPORTS_DIR*
rm -rf $GATLING_REPORT_DIR*
rm -rf $GATLING_USERFILES_DIR/*

mkdir -p $GATHER_REPORTS_DIR

cp -R $GATLING_WORK_DIR/ $GATLING_USERFILES_DIR/

if [[ "$(declare -p HOSTS)" =~ "declare -a" ]]; then
  for HOST in "${HOSTS[@]}"
  do
    echo "Cleaning previous runs from host: $HOST"
    ssh -n -f $USER_NAME@$HOST "sh -c 'rm -rf $GATHER_REPORTS_DIR*'"
    ssh -n -f $USER_NAME@$HOST "sh -c 'rm -rf $GATLING_REPORT_DIR*'"
    ssh -n -f $USER_NAME@$HOST "sh -c 'rm -rf $GATLING_USERFILES_DIR/*'"
    sleep 10
  done
fi

if [[ "$(declare -p HOSTS)" =~ "declare -a" ]]; then
  for HOST in "${HOSTS[@]}"
  do
    echo "Copying simulations to host: $HOST"
    scp -r $GATLING_WORK_DIR/* $USER_NAME@$HOST:$GATLING_USERFILES_DIR/
    sleep 10
  done
fi

if [[ "$(declare -p HOSTS)" =~ "declare -a" ]]; then
  for HOST in "${HOSTS[@]}"
  do
    echo "Running simulation on host: $HOST"
    ssh -n -f $USER_NAME@$HOST "export JAVA_OPTS='$JAVA_OPTS' && sh -c 'nohup $GATLING_RUNNER -nr -s $SIMULATION_NAME --run-description $REPORT_DESCRIPTION > /tmp/run.log 2>&1 &'"
  done
fi

echo "Running simulation on localhost"
$GATLING_RUNNER -nr -s $SIMULATION_NAME --run-description $REPORT_DESCRIPTION

if [[ "$(declare -p HOSTS)" =~ "declare -a" ]]; then
  echo "Wait other hosts"
  sleep 30
fi

echo "Gathering result file from localhost"
ls -t $GATLING_REPORT_DIR | head -n 1 | xargs -I {} mv ${GATLING_REPORT_DIR}{} ${GATLING_REPORT_DIR}report
cp ${GATLING_REPORT_DIR}report/simulation.log $GATHER_REPORTS_DIR

if [[ "$(declare -p HOSTS)" =~ "declare -a" ]]; then
  for HOST in "${HOSTS[@]}"
  do
    echo "Gathering result file from host: $HOST"
    ssh -n -f $USER_NAME@$HOST "sh -c 'ls -t $GATLING_REPORT_DIR | head -n 1 | xargs -I {} mv ${GATLING_REPORT_DIR}{} ${GATLING_REPORT_DIR}report'"
    sleep 20
    scp $USER_NAME@$HOST:${GATLING_REPORT_DIR}report/simulation.log ${GATHER_REPORTS_DIR}simulation-$HOST.log
    sleep 5
  done
fi

mv $GATHER_REPORTS_DIR $GATLING_REPORT_DIR
echo "Aggregating simulations"
$GATLING_RUNNER -ro reports

now=`date +"%Y-%m-%d"_%H-%M-%S`
mkdir -p $OUTPUT_DIR/${now}
cp -R ${GATLING_REPORT_DIR}reports/* $OUTPUT_DIR/${now}

echo "Report saved in $OUTPUT_DIR/${now}"
echo "Please open the following file $OUTPUT_DIR/${now}/index.html"

# using macOSX
#open $HOME/gatling_reports/${now}/index.html

# using ubuntu
#google-chrome ${GATLING_REPORT_DIR}reports/index.html
