FROM openjdk:8-jdk-alpine
# Installing the application
COPY ${JAR_FILE} /app.jar
COPY ${GOOGLE_APPLICATION_CREDENTIALS} /firebase.json
ENTRYPOINT ["java","-jar","/app.jar"]