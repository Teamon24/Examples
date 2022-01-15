#!/usr/bin/bash

. ./vars.sh
. ./scripts/bash/functions.sh

PACKAGE="python3-numpy"
command "installIfAbsent "$PACKAGE""
installIfAbsent "$PACKAGE"

ENV_FILE="./${ENV_FILE}"
DOCKER_COMPOSE_TEMPLATE_FILE="./${DOCKER_COMPOSE_TEMPLATE_FILE}"
DOCKER_COMPOSE_FILE="./${DOCKER_COMPOSE_FILE}"
PYTHON_SCRIPT="./scripts/run.py"

echo "PYTHON_SCRIPT: ${PYTHON_SCRIPT}"
echo "ENV_FILE: ${ENV_FILE}"
echo "DOCKER_COMPOSE_TEMPLATE_FILE: ${DOCKER_COMPOSE_TEMPLATE_FILE}"
echo "DOCKER_COMPOSE_FILE: ${DOCKER_COMPOSE_FILE}"

command "python3 ${PYTHON_SCRIPT} ..."
python3 \
"${PYTHON_SCRIPT}" \
"${ENV_FILE}" \
"${DOCKER_COMPOSE_TEMPLATE_FILE}" \
"${DOCKER_COMPOSE_FILE}"

command "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d"
docker-compose -f "${DOCKER_COMPOSE_FILE}" up -d
