# io-loadtest

This a project to run load test on IO infrastucture.
You can run installing it on a linux/ubuntu environment or run with docker.

## Run on machine

### 1. Install gatling

Install gatling on hosts.

```bash
# Current GATLING_VERSION=3.3.1
cd io-loadtest
sudo bash utils/install_gatling.sh GATLING_VERSION
source /etc/environment
```

### 2. Set vars

Set global and specific vars.

```bash
# global vars
cd io-loadtest
cp vars_sample.sh vars.sh
# specific vars
cp uc1/vars_sample.sh uc1/vars.sh
```

### 3. Run

Execute load test.

```bash
cd io-loadtest
bash run.sh uc1/vars.sh
```

## Run with Docker

### 1. Build Docker image

```bash
cd io-loadtest
bash utils/build_docker.sh
```

### 2. Run

```bash
# Set GATLING_VARS to point to configuration file, for example:
# -e GATLING_VARS="uc1/vars_sample.sh"

# if you need you can override vars in GATLING_VARS configuration file, for example:
# export JAVA_OPTS="${JAVA_OPTS} -DsessionToken=XXX"

cd io-loadtest
docker run --rm -it \
       -e GATLING_VARS="uc1/vars_sample.sh" \
       -e JAVA_OPTS \
       -v $(pwd)/gatling_reports:/root/gatling_reports \
       io-loadtest:v0.1
```

## UC's index

1. [UC1 - app-backend GET /info](uc1/README.md)
1. [UC2 - api-gad GET /ping](uc2/README.md)
1. [UC3 - api GET /test/echo-request](uc3/README.md)
1. [UC4 - app-backend GET /api/v1/profile](uc4/README.md)
1. [UC5 - app-backend GET /api/v1/messages](uc5/README.md)
1. [UC6 - app-backend GET /api/v1/messages/{id_message}](uc6/README.md)
1. [UC7 - api GET /api/v1/profiles/{fiscalcode}](uc7/README.md)
1. [UC8 - api POST /api/v1/messages/{fiscalcode}](uc8/README.md)
1. [UCAPP1 - OpenAppIO and Read Messages](ucapp1/README.md)

## License
Please refer to [IO license agreement](https://github.com/pagopa/io-app/blob/master/LICENSE).
