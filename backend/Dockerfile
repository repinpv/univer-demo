FROM openjdk:21-jdk
COPY target/${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]