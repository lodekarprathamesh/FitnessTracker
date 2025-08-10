# ===== Build Stage =====
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ===== Runtime Stage =====
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/FitnessTracker.jar FitnessTracker.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "FitnessTracker.jar" ,"--server.port=8082"]
