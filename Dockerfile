# ===== Stage 1: Build the app =====
FROM maven:3.9.6-eclipse-temurin-17-alpine as builder

WORKDIR /app

# Copy pom.xml and download dependencies first
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source
COPY src ./src

# Build app (skip tests for faster build)
RUN mvn clean package -DskipTests

# ===== Stage 2: Run the app =====
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copy the executable JAR
COPY --from=builder /app/target/Prayer-Connect-1.0-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
