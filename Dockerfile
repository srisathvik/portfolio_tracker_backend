FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /app/target/portfolio-tracker-0.0.1-SNAPSHOT.jar portfolio-tracker-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "portfolio-tracker-0.0.1-SNAPSHOT.jar"]