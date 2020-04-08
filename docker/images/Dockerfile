FROM openjdk:8-jdk-alpine

# gating version
ENV GATLING_VERSION 3.3.1

# create directory for gatling install
RUN mkdir -p /opt/gatling_$GATLING_VERSION

# install gatling
RUN apk add --update wget bash libc6-compat git && \
    mkdir -p /tmp/downloads && \
    wget -q -O /tmp/downloads/gatling-$GATLING_VERSION.zip \
    https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/$GATLING_VERSION/gatling-charts-highcharts-bundle-$GATLING_VERSION-bundle.zip && \
    mkdir -p /tmp/archive && cd /tmp/archive && \
    unzip /tmp/downloads/gatling-$GATLING_VERSION.zip && \
    mv /tmp/archive/gatling-charts-highcharts-bundle-$GATLING_VERSION/* /opt/gatling_$GATLING_VERSION && \
    rm -rf /tmp/*

# set environment variables
ENV PATH /opt/gatling_$GATLING_VERSION/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
ENV GATLING_HOME /opt/gatling_$GATLING_VERSION

# get pagopa/io-loadtest
RUN git clone https://github.com/pagopa/io-loadtest

WORKDIR /io-loadtest

# set entrypoint script
RUN echo "#!/bin/bash" >> entrypoint.sh && \
    echo "git pull && cp vars_sample.sh vars.sh && ./run.sh "'$GATLING_VARS'"" >> entrypoint.sh && \
    chmod 700 entrypoint.sh && \
    chmod 700 run.sh

ENTRYPOINT [ "/io-loadtest/entrypoint.sh" ]
