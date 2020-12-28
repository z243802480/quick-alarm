FROM java:8
VOLUME /tmp
WORKDIR /home/app
ADD core-1.0-fat.jar alarm.jar
ADD alarmConfig alarmConfig
RUN bash -c 'touch /alarm.jar'
EXPOSE 8888
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","alarm.jar"]