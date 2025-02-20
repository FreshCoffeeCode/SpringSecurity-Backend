#!/usr/bin/env bash

mvn clean package

rm -f src/main/docker/*.jar

cp target/*.jar src/main/docker/

cd src/main/docker

docker image prune -f

cd ../../..

docker build . -t spring-security

cd src/main/docker

rm *.jar

cd ../../..

echo "Build Done!"