# Use Java 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy JAR file from Maven target
COPY target/expense_tracker-0.0.1-SNAPSHOT.jar

# Expose port (from .env)
EXPOSE 8081

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
