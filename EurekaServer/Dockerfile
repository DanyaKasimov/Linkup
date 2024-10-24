FROM openjdk:22-ea-19-jdk-slim AS build

COPY /target/EurekaServer-0.0.1-SNAPSHOT.jar eureka.jar
EXPOSE 8761

ENTRYPOINT ["java","-jar","eureka.jar"]