#!/bin/bash

export OUTPUT_DIR=$HOME/gatling_reports/$HOSTNAME

git pull && cp vars_sample.sh vars.sh && ./run.sh $GATLING_VARS
