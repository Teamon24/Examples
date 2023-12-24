#!/usr/bin/bash

. ./scripts/bash/functions.sh
. ./vars.sh

log "psql -h ${HOST} -p ${PORT} -U ${USER_NAME} -W ${PASSWORD}"
psql -h "${HOST}" -p "${PORT}" -U "${USER_NAME}" -W "${PASSWORD}"

