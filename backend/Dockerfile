FROM amazoncorretto:21-alpine

MAINTAINER "adis@flinksoftware.nl"

COPY build/libs/demo-*.jar app.jar

RUN sh -c 'touch /app.jar' && adduser --system java

ENV JAVA_OPTS=""

USER java
EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -Duser.timezone=Europe/Amsterdam $JAVA_OPTS -jar /app.jar" ]
