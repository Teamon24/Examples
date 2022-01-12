#!/usr/bin/bash

. ../scripts/bash/functions.sh
. ./vars.sh

command "psql -h ${HOST} -p ${PORT} -U ${USER_NAME} -W ${PASSWORD}"
psql -h "${HOST}" -p "${PORT}" -U "${USER_NAME}" -W "${PASSWORD}"

