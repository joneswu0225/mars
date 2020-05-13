#!/bin/sh
mvn clean install -DskipTests=True
scp target/mars-0.0.1-SNAPSHOT.jar pano@47.102.199.232:/home/pano/projects/sh_msa/sh_msa_tmp.jar
ssh pano@47.102.199.232 "cd projects/sh_msa; rm sh_msa.jar.bak; mv sh_msa.jar sh_msa.jar.bak;mv sh_msa_tmp.jar sh_msa.jar"
ssh pano@47.102.199.232 "ps -ef| grep 'sh_msa'|grep -v grep|cut -c 9-15|xargs kill -9"
ssh pano@47.102.199.232 "cd projects/sh_msa; nohup /usr/local/jdk/bin/java -jar sh_msa.jar --spring.config.location=./application.properties 1>out.txt 2>&1 &"