#!/usr/bin/bash

. ./vars.sh


POSTGRES_VOLUME="$(echo "${POSTGRES_CONTAINER_NAME}" | xargs -I {} docker inspect -f '{{ range .Mounts }}{{ .Name }}{{ end }}' {})"

docker container stop "${POSTGRES_CONTAINER_NAME}"
docker container rm "${POSTGRES_CONTAINER_NAME}"

docker volume rm "$POSTGRES_VOLUME"
echo "123" | sudo -S chmod -R 777 "${HOST_POSTGRES_VOLUME_PATH}"

echo "rm -Rf ${HOST_POSTGRES_VOLUME_PATH}"
rm -Rf "${HOST_POSTGRES_VOLUME_PATH}"
