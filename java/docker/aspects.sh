#!/usr/bin/bash

. ./scripts/bash/functions.sh


CURRENT_DIR=$(cd ../ && pwd)
BUILD="$CURRENT_DIR/build/classes/java/main"
ROOT_PATH="aop/ex2"

EXAMPLE_PATH="$CURRENT_DIR/src/main/java/$ROOT_PATH"

AJC="ajc -sourceroots $EXAMPLE_PATH/aspects/:$EXAMPLE_PATH/targets/ -d $BUILD -showWeaveInfo"
logAndEval "$AJC"

RESOURCES="$CURRENT_DIR/src/main/resources"
ASPECTJ_JAR="$RESOURCES/libs/aspectjrt-1.9.6.jar"

ENTRY_POINT_CLASS_NAME="$(echo "$ROOT_PATH/targets/Main" | sed -r 's/[/]+/./g')"
JAVA_CP="java -classpath $BUILD:$ASPECTJ_JAR $ENTRY_POINT_CLASS_NAME"
logAndEval "$JAVA_CP"
