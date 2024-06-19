# Use the official Maven image as the build stage
FROM maven:3.8.4-openjdk-17-slim AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use a lightweight Java image as the final stage
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file from the builder stage to the container
COPY --from=builder /app/target/hotel-management-system-0.0.1-SNAPSHOT.jar .

# Expose the port that the application listens to
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "hotel-management-system-0.0.1-SNAPSHOT.jar"]
