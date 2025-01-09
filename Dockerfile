FROM openjdk:17-jdk-slim


COPY target/portfolio-tracker-0.0.1-SNAPSHOT.jar portfolio-tracker-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "portfolio-tracker-0.0.1-SNAPSHOT.jar"]