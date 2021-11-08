#!/bin/sh
mvn clean install -DskipTests=True
scp target/mars-0.0.1-SNAPSHOT.jar pano@47.101.191.248:/home/pano/zhongchuang/backend/en_tutor_tmp.jar
ssh pano@47.101.191.248 "cd /home/pano/zhongchuang/backend; rm en_tutor.jar.bak; mv en_tutor.jar en_tutor.jar.bak;mv en_tutor_tmp.jar en_tutor.jar"
ssh pano@47.101.191.248 "ps -ef| grep 'en_tutor'|grep -v grep|cut -c 9-15|xargs kill -9"
ssh pano@47.101.191.248 "cd /home/pano/zhongchuang/backend; nohup /usr/local/jdk/bin/java -jar en_tutor.jar --spring.config.location=./application.properties 1>out.txt 2>&1 &"