ENV_FILE=vars.sh
DOCKER_COMPOSE_TEMPLATE_FILE=docker-compose.template.yml
DOCKER_COMPOSE_FILE=docker-compose.yml

#VOLUME
POSTGRES_VOLUME_NAME=examples_jpa_postgres
HOST_POSTGRES_VOLUME_PATH=/var/lib/docker/volumes/examples/jpa/postgres

MYSQL_VOLUME_NAME=examples_jpa_mysql
HOST_MYSQL_VOLUME_PATH=/var/lib/docker/volumes/examples/jpa/mysql
#VOLUME ends

#NAMES
POSTGRES_SERVICE_NAME=examples-jpa-postgres
MYSQL_SERVICE_NAME=examples-jpa-mysql

POSTGRES_CONTAINER_NAME=examples-jpa-postgres-container
MYSQL_CONTAINER_NAME=examples-jpa-mysql-container
#NAMES ends

POSTGRES_HOST=localhost
POSTGRES_PASSWORD=selectel
POSTGRES_USER_NAME=selectel
POSTGRES_DATABASE=selectel

MYSQL_HOST=localhost
MYSQL_PASSWORD=selectel
MYSQL_USER_NAME=selectel
MYSQL_DATABASE=selectel

POSTGRES_INIT_SCRIPTS=./init.sql
CONTAINER_POSTGRES_INIT_SCRIPTS=/docker-entrypoint-initdb.d/init.sql

POSTGRES_DATA=./volumes/postgres/data
CONTAINER_POSTGRES_DATA=/var/lib/postgresql/data

MYSQL_DATA=./volumes/mysql/oradata
CONTAINER_MYSQL_DATA=/var/lib/mysql

POSTGRES_PORT=5432
CONTAINER_POSTGRES_PORT=5432

MYSQL_PORT=5431
CONTAINER_MYSQL_PORT=5431


