#!/bin/sh
mvn clean install -DskipTests=True
ssh jones@47.102.199.232 "cd projects/mars; rm mars.jar.bak; mv mars.jar mars.jar.bak;"
scp target/mars-0.0.1-SNAPSHOT.jar jones@47.102.199.232:/home/jones/projects/mars/mars.jar
ssh ubuntu@123.207.49.36 "ps -ef| grep 'mars'|grep -v grep|cut -c 9-15|xargs kill -9"
ssh ubuntu@123.207.49.36 "cd projects/mars; nohup /usr/java8/bin/java -jar mars.jar --spring.config.location=./application.properties 1>out.txt 2>&1 &"