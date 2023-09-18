#!/bin/bash

IFS=$'\n' read -d '' -r -a lines

./gradlew clean build

java -jar ./build/libs/orderbook-1.0-SNAPSHOT.jar "${lines[@]}"