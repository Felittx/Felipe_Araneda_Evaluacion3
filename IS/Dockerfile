FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/evaluacion3-0.0.1.jar
COPY target/evaluacion3-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
