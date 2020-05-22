FROM openjdk:8-jdk-alpine
RUN addgroup -S stalker && adduser -S stalker -G stalker
USER stalker:stalker
ARG JAR_FILE=target/*.jar
# Copying files into the container
COPY ${JAR_FILE} /stalker-backend.jar
ENTRYPOINT ["java","-jar","/stalker-backend.jar"]