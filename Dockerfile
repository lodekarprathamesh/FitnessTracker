# ===== Build Stage =====
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ===== Runtime Stage =====
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/FitnessTracker.jar FitnessTracker.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "FitnessTracker.jar" ,"--server.port=8082"]
