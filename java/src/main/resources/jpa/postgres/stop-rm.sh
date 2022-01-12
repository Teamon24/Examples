#!/usr/bin/bash

. ./vars.sh

docker container stop "${CONTAINER_NAME}"
docker container rm "${CONTAINER_NAME}"