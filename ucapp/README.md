# UCAPP - AppIO Use Cases

This folder contains AppIO's Use Cases.

Each simulation (i.e. `ucapp1.scala`) combines transactions to implement an use case.

    .
    ├── resources                         # external resources files needed to customize the simulation
    │   └── ...
    ├── simulations
    │   ├── transactions                  # trascations folder
    │   │   ├── CloseApp.scala            # close app transaction
    │   │   ├── OpenApp.scala             # open app transaction
    │   │   ├── UnlockApp.scala           # unlock app transaction
    │   │   └── ...
    │   ├── Configuration.scala           # set vars configuration
    │   └── ucapp1.scala                  # simmulation file that combine transactions to implement an use case
    ├── README.md
    ├── ucapp1-vars_sample.sh             # sample vars for ucapp1.scala simulation
    └── ucapp1-vars-Xvus.scala            # vars for ucapp1.scala simulation with X virtual users

## UCAPP1 - OpenAppIO and Read Messages

This use case implements a user that open AppIO and reads messages.

### Run on host

```bash
cd io-loadtest

# copy vars
cp vars_sample.sh vars.sh

# export sessionToken
 export JAVA_OPTS="${JAVA_OPTS} -DsessionToken=XXX"

# run test
bash run.sh ucapp/ucapp1-vars_sample.sh
```

### Run with docker

```bash
cd io-loadtest

# build docker image
bash utils/build_docker.sh

# export sessionToken
 export JAVA_OPTS="${JAVA_OPTS} -DsessionToken=XXX"

# run test
docker run --rm -it \
       -e GATLING_VARS="ucapp/ucapp1-vars_sample.sh" \
       -e JAVA_OPTS \
       -v $(pwd)/gatling_reports:/root/gatling_reports \
       io-loadtest:v0.1
```
