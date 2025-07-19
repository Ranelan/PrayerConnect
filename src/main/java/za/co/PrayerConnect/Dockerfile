# Use a lightweight OpenJDK image with Java 17
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy your built jar file into the container
COPY target/Prayer-Connect-1.0-SNAPSHOT.jar app.jar

# Expose port 8080 (default Spring Boot port)
EXPOSE 8081

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
