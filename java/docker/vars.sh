ENV_FILE=vars.sh

PROJECT_NAME=examples_postrges_project
DOCKER_COMPOSE_TEMPLATE_FILE=docker-compose.template.yml
DOCKER_COMPOSE_FILE=docker-compose.yml

#VOLUME
POSTGRES_VOLUME_NAME=examples_jpa_postgres_volume
HOST_POSTGRES_VOLUME_PATH=/var/lib/docker/volumes/examples/jpa/postgres/
#VOLUME ends

#NAMES
POSTGRES_SERVICE_NAME=examples-jpa-postgres
POSTGRES_CONTAINER_NAME=examples-jpa-postgres-container
#NAMES ends


POSTGRES_HOST=localhost
POSTGRES_PASSWORD=selectel
POSTGRES_USER_NAME=selectel
POSTGRES_DATABASE=selectel

POSTGRES_INIT_SCRIPTS_0=./sql_scripts/0.schemas.sql
POSTGRES_INIT_SCRIPTS_1=./sql_scripts/1.example.sql
POSTGRES_INIT_SCRIPTS_2=./sql_scripts/2.example.sql
POSTGRES_INIT_SCRIPTS_4=./sql_scripts/4.example.sql

CONTAINER_POSTGRES_INIT_SCRIPTS_0=/docker-entrypoint-initdb.d/0.schemas.sql
CONTAINER_POSTGRES_INIT_SCRIPTS_1=/docker-entrypoint-initdb.d/1.example.sql
CONTAINER_POSTGRES_INIT_SCRIPTS_2=/docker-entrypoint-initdb.d/2.example.sql
CONTAINER_POSTGRES_INIT_SCRIPTS_4=/docker-entrypoint-initdb.d/4.example.sql


POSTGRES_DATA=./volumes/postgres/data/examples
CONTAINER_POSTGRES_DATA=/var/lib/postgresql/data/examples

POSTGRES_PORT=5432
CONTAINER_POSTGRES_PORT=5432


