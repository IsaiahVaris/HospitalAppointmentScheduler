FROM openjdk:11
VOLUME /tmp
EXPOSE 9090
ADD hospitalappointmentscheduler-0.0.1-SNAPSHOT.jar hospitalappointmentscheduler-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "hospitalappointmentscheduler-0.0.1-SNAPSHOT.jar"]

