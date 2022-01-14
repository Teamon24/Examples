#!/usr/bin/bash

. ./vars.sh

docker container stop "${POSTGRES_CONTAINER_NAME}"
docker container rm "${POSTGRES_CONTAINER_NAME}"

docker container stop "${MYSQL_CONTAINER_NAME}"
docker container rm "${MYSQL_CONTAINER_NAME}"