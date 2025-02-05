# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven build JAR from target directory to the container
COPY target/server-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
