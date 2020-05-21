FROM openjdk:8-jdk-alpine
RUN addgroup -S stalker && adduser -S stalker -G stalker
USER stalker:stalker
ARG JAR_FILE=target/*.jar
#ARG GOOGLE_APPLICATION_CREDENTIALS=firebase*.json
# Copying files into the container
COPY ${JAR_FILE} /stalker-backend.jar
# COPY ${GOOGLE_APPLICATION_CREDENTIALS} /firebase.json
ENTRYPOINT ["java","-jar","/stalker-backend.jar"]