#!/bin/sh
mvn clean install -DskipTests=True
scp target/mars-0.0.1-SNAPSHOT.jar pano@47.102.199.232:/data1/project/haite/backend/haite_tmp.jar
ssh pano@47.102.199.232 "cd /data1/project/haite/backend; rm haite.jar.bak; mv haite.jar haite.jar.bak;mv haite_tmp.jar haite.jar"
ssh pano@47.102.199.232 "ps -ef| grep 'haite'|grep -v grep|cut -c 9-15|xargs kill -9"
ssh pano@47.102.199.232 "cd /data1/project/haite/backend; nohup /usr/local/jdk/bin/java -jar haite.jar --spring.config.location=./application.properties 1>out.txt 2>&1 &"