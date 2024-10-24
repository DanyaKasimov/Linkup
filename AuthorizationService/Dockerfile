FROM openjdk:22-ea-19-jdk-slim AS build

COPY /target/AuthorizationService-1.0-SNAPSHOT.jar app.jar
EXPOSE 8090

ENTRYPOINT ["java","-jar","app.jar"]
