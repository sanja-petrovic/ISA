FROM openjdk:17-jdk-alpine
COPY /target/isa-0.0.1-SNAPSHOT.jar api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/api.jar"]
