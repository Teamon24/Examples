ENV_FILE=vars.sh
DOCKER_COMPOSE_TEMPLATE_FILE=docker-compose.yml.template
DOCKER_COMPOSE_FILE=docker-compose.yml

VOLUME_NAME=postgres_project
SERVICE_NAME=examples-postgres
CONTAINER_NAME=examples-postgres-container

HOST=localhost
PASSWORD=selectel
USER_NAME=selectel
DATABASE=selectel

INIT_SCRIPTS=./init.sql
CONTAINER_INIT_SCRIPTS=/docker-entrypoint-initdb.d/init.sql
POSTGRES_DATA=./postgres_data
CONTAINER_POSTGRES_DATA=/var/lib/postgresql/data

PORT=5432
CONTAINER_PORT=5432
