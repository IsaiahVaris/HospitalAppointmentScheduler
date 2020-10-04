FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine-slim

VOLUME /tmp

EXPOSE 8080

ADD hospitalappointmentscheduler-0.0.1-SNAPSHOT.jar hospitalappointmentscheduler-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "hospitalappointmentscheduler-0.0.1-SNAPSHOT.jar"]
