# io-loadtest

## Install gatling

Install gatling on hosts.

```
# Current GATLING_VERSION=3.3.1
sudo bash io-loadtest/utils/install_gatling.sh GATLING_VERSION
source /etc/environment
```

## Set vars

Set global and specific vars.

```
# global vars
cp io-loadtest/vars_sample.sh io-loadtest/vars.sh
# specific vars
cp io-loadtest/uc1/vars_sample.sh io-loadtest/uc1/vars.sh
```

## Run

Execute load test.

```
bash io-loadtest/run.sh uc1/vars.sh
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