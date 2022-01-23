#!/usr/bin/bash

. ./scripts/bash/functions.sh


CURRENT_DIR=$(cd ../ && pwd)
BUILD="$CURRENT_DIR/build/classes/java/main"
MODULE_PATH="$CURRENT_DIR/src/main/java"


EX2_PATH="aop/ex2"
EXAMPLE_PATH="$MODULE_PATH/$EX2_PATH"

RESOURCES="$CURRENT_DIR/src/main/resources"
DEPENDENCIES="$RESOURCES/dependecies"

LANG3="$DEPENDENCIES/commons-lang3-3.12.0.jar"
ASPECTJ_JAR="$DEPENDENCIES/aspectjrt-1.9.6.jar"

logAndEval \
"ajc \\
-sourceroots $MODULE_PATH/utils:$EXAMPLE_PATH/aspects/:$EXAMPLE_PATH/targets/ \\
-d $BUILD \\
-showWeaveInfo -1.8"


ENTRY_POINT_CLASS_PATH="$EX2_PATH/targets/Main"
ENTRY_POINT_CLASS_NAME="$(echo "$ENTRY_POINT_CLASS_PATH" | sed -r 's/[/]+/./g')"

logAndEval \
"java \\
-classpath $BUILD:$ASPECTJ_JAR $ENTRY_POINT_CLASS_NAME"
