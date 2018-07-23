#!/bin/bash
CLASSPATH=.:resources:resources/static:resources/templates:resources/mybatis:$CLASSPATH
DEBUG='-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044'
export CLASSPATH=$CLASSPATH
echo $CLASSPATH
java -jar panorama.jar --spring.config.location=./resources/application.properties
#nohup java -jar panorama.jar --spring.config.location=./resources/application.properties
#nohup java -jar panorama.jar --spring.config.location=./resources/application.properties 1>out.txt 2>&1
#nohup java -jar panorama.jar 1>out.txt 2>&1