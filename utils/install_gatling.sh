#!/bin/bash

# gating version
GATLING_VERSION=$1

if [ -z ${1+x} ]; then 
    echo "GATLING_VERSION is unset, visit https://gatling.io/open-source/"
    exit 1
else
    echo "GATLING_VERSION is set to '$GATLING_VERSION'"
fi

# create directory for gatling install
GATLING_HOME=/opt/gatling_$GATLING_VERSION
rm -rf $GATLING_HOME/*
mkdir -p $GATLING_HOME

# install gatling
apt-get update && apt-get install wget openjdk-8-jdk unzip -y && \
  mkdir -p /tmp/downloads && \
  wget -q -O /tmp/downloads/gatling-$GATLING_VERSION.zip \
  https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/$GATLING_VERSION/gatling-charts-highcharts-bundle-$GATLING_VERSION-bundle.zip && \
  mkdir -p /tmp/archive && cd /tmp/archive && \
  unzip /tmp/downloads/gatling-$GATLING_VERSION.zip && \
  mv /tmp/archive/gatling-charts-highcharts-bundle-$GATLING_VERSION/* $GATLING_HOME && \
  rm -rf /tmp/* && \
  mkdir -p $GATLING_HOME/target && \
  mkdir -p $GATLING_HOME/results && \
  mkdir -p $GATLING_HOME/reports && \
  chown -R root:adm $GATLING_HOME && \
  chmod -R 775 $GATLING_HOME && \
  sed -i '/^GATLING_HOME/d' /etc/environment && \
  echo "GATLING_HOME=$GATLING_HOME" >> /etc/environment

exit 0
