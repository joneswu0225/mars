#!/bin/sh
mvn clean install -DskipTests=True
scp target/mars-0.0.1-SNAPSHOT.jar pano@47.102.199.232:/data1/project/yzj/backend/yzj_tmp.jar
ssh pano@47.102.199.232 "cd /data1/project/yzj/backend; rm yzj.jar.bak; mv yzj.jar yzj.jar.bak;mv yzj_tmp.jar yzj.jar"
ssh pano@47.102.199.232 "ps -ef| grep 'yzj'|grep -v grep|cut -c 9-15|xargs kill -9"
ssh pano@47.102.199.232 "cd /data1/project/yzj/backend; nohup /usr/local/jdk/bin/java -jar yzj.jar --spring.config.location=./application.properties 1>out.txt 2>&1 &"