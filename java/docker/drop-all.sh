#!/usr/bin/bash

. ./vars.sh


POSTGRES_VOLUME="$(echo "${POSTGRES_CONTAINER_NAME}" | xargs -I {} docker inspect -f '{{ range .Mounts }}{{ .Name }}{{ end }}' {})"
MYSQL_VOLUME="$(echo "${MYSQL_CONTAINER_NAME}" | xargs -I {} docker inspect -f '{{ range .Mounts }}{{ .Name }}{{ end }}' {})"

./stop-rm.sh

docker volume rm "$MYSQL_VOLUME"
docker volume rm "$POSTGRES_VOLUME"

echo "rm -Rf ${HOST_POSTGRES_VOLUME_PATH}"
echo "rm -Rf ${HOST_MYSQL_VOLUME_PATH}"

echo "123" | sudo chmod -R 777 "${HOST_POSTGRES_VOLUME_PATH}"
echo "123" | sudo chmod -R 777 "${HOST_MYSQL_VOLUME_PATH}"

rm -Rf "${HOST_POSTGRES_VOLUME_PATH}"
rm -Rf "${HOST_MYSQL_VOLUME_PATH}"