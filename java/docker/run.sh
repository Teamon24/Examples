. ./vars.sh

command "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d"
docker-compose -f "${DOCKER_COMPOSE_FILE}" up -d