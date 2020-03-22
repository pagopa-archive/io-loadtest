# io-loadtest

## Install gatling

Install gatling on hosts.

```
# Current GATLING_VERSION is 3.3.1
sudo bash ./utils/install_gatling.sh GATLING_VERSION
```

## Set vars

Set global and specific vars.

```
# global vars
cp vars.sample.sh vars.sh
# specific vars
cp uc1/vars.sample.sh uc1/vars.sh
```

## Run

Execute load test.

```
bash io-loadtest/run.sh uc1/vars.sh
```
