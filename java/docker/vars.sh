ENV_FILE=vars.sh
DOCKER_COMPOSE_TEMPLATE_FILE=docker-compose.template.yml
DOCKER_COMPOSE_FILE=docker-compose.yml

#VOLUME
POSTGRES_VOLUME_NAME=examples_jpa_postgres_volume
HOST_POSTGRES_VOLUME_PATH=/var/lib/docker/volumes/examples/jpa/postgres

MYSQL_VOLUME_NAME=examples_jpa_mysql_volume
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

POSTGRES_INIT_SCRIPTS_0=./sql_scripts/0.schemas.sql
POSTGRES_INIT_SCRIPTS_1=./sql_scripts/1.example.sql
POSTGRES_INIT_SCRIPTS_2=./sql_scripts/2.example.sql
POSTGRES_INIT_SCRIPTS_4=./sql_scripts/4.example.sql

CONTAINER_POSTGRES_INIT_SCRIPTS_0=/docker-entrypoint-initdb.d/0.schemas.sql
CONTAINER_POSTGRES_INIT_SCRIPTS_1=/docker-entrypoint-initdb.d/1.example.sql
CONTAINER_POSTGRES_INIT_SCRIPTS_2=/docker-entrypoint-initdb.d/2.example.sql
CONTAINER_POSTGRES_INIT_SCRIPTS_4=/docker-entrypoint-initdb.d/4.example.sql


POSTGRES_DATA=./volumes/postgres/data
CONTAINER_POSTGRES_DATA=/var/lib/postgresql/data

MYSQL_DATA=./volumes/mysql/oradata
CONTAINER_MYSQL_DATA=/var/lib/mysql

POSTGRES_PORT=5432
CONTAINER_POSTGRES_PORT=5432

MYSQL_PORT=5431
CONTAINER_MYSQL_PORT=5431


