FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
ADD . /src/main/resources/files
COPY target/employbackend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]