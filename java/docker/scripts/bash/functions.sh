#!/usr/bin/bash

function installIfAbsent() {
  REQUIRED_PKG=$1
  PKG_OK=$(dpkg-query -W --showformat='${Status}\n' $REQUIRED_PKG | grep "installed")
  echo Checking for $REQUIRED_PKG: $PKG_OK
  if [ "" = "$PKG_OK" ]; then
    echo "No $REQUIRED_PKG. Setting up $REQUIRED_PKG."
    sudo apt-get --yes install $REQUIRED_PKG
  fi
}

function deleteNewLine() {
  value=$1
  echo "${value//[$'\t\r\n ']/}"
}

function replace() {
  fileName=$1
  key=$2
  value=$3
  sed 's/${'"${key}"'}/'"${value}"'/' <<<"${fileName}"
}

LINE_LIMIT="60"

function command() {
  commandString=$1
  word="EXECUTING"
  line="$(repeat '=' "$(("${#word}" + 1))")$(repeat '-' "${#commandString}")"
  echo "${line}"
  echo "$word:" "$commandString"
  echo "${line}"
}

function logAndEval() {
  commandString=$1
  word="EXECUTING"
  line="$(repeat '=' "$(("${#word}" + 1))")$(repeat '-' "$LINE_LIMIT")"

  echo ""
  echo "${line}"
  echo "$word:" "$commandString"
  echo "${line}"
  echo ""

  eval "$1"
}

function repeat() {
  symbol=$1
  n=$2
  for i in $(seq 1 "$n"); do echo -n "$symbol"; done
}

function aspect() {

}
