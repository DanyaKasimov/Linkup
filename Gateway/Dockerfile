FROM openjdk:22-ea-19-jdk-slim AS build

COPY /target/Gateway-0.0.1-SNAPSHOT.jar gateway.jar
EXPOSE 8787

ENTRYPOINT ["java","-jar","gateway.jar"]