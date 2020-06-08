FROM openjdk:8-jdk-alpine
# Adding user
RUN addgroup -S stalker && adduser -S stalker -G stalker
USER stalker:stalker
# Copying files into the container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /stalker-backend.jar
ENTRYPOINT ["java","-jar","/stalker-backend.jar"]