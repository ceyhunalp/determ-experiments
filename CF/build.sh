#!/bin/sh

PWD=`pwd`
SRC_DIR=$PWD'/src'
TARGET_DIR=$PWD'/target'
JAR_DIR=$PWD'/jars'
CP=$JAR_DIR'/*'

export CLASSPATH=$CP

if [ -d "$TARGET_DIR" ];
then
        rm -rf $TARGET_DIR
        mkdir $TARGET_DIR
fi

if ! [ -d "$JAR_DIR" ];
then
        mvn -DoutputDirectory=$JAR_DIR dependency:copy-dependencies
fi

srcs=""
for f in $(find . -type f -name '*.java'); do
        srcs+="$f "
done

echo $srcs
javac -d $TARGET_DIR -sourcepath . $srcs
