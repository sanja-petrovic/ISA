FROM openjdk:17-jdk-alpine
COPY /target/LocationSimulator-0.0.1-SNAPSHOT.jar location-simulator.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/location-simulator.jar"]
