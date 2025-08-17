FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/auth-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]