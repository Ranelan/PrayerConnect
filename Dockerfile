# ===== Stage 1: Build the app =====
FROM maven:3.9.6-eclipse-temurin-17-alpine as builder

WORKDIR /app

# Copy pom.xml and download dependencies first (to cache them)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# ===== Stage 2: Run the app =====
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8081

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
