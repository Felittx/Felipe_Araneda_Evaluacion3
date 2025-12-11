FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/evaluacion3_Felipe_Araneda-0.0.1.jar
COPY ${JAR_FILE} ev3_fa.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ev3_fa.jar"]