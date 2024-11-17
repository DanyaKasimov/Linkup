FROM openjdk:22-ea-19-jdk-slim

COPY /target/ApplicationService-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8095

ENTRYPOINT ["java","-jar","app.jar"]
