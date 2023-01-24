FROM openjdk:17-jdk-alpine
COPY /target/LocationSimulator-0.0.1-SNAPSHOT.jar location-simulator.jar
EXPOSE 8081
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:7007","-jar","/location-simulator.jar"]
