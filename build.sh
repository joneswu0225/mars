#!/bin/sh
mvn clean install -DskipTests=True
scp target/mars-0.0.1-SNAPSHOT.jar pano@47.102.199.232:/home/pano/projects/mars/mars_tmp.jar
ssh pano@47.102.199.232 "cd projects/mars; rm mars.jar.bak; mv mars.jar mars.jar.bak;mv mars_tmp.jar mars.jar"
ssh pano@47.102.199.232 "ps -ef| grep 'mars'|grep -v grep|cut -c 9-15|xargs kill -9"
ssh pano@47.102.199.232 "cd projects/mars; nohup /usr/local/jdk/bin/java -jar mars.jar --spring.config.location=./application.properties 1>out.txt 2>&1 &"