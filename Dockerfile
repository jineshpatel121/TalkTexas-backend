# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-jammy as builder

# Set the working directory
WORKDIR /app

# Copy the Maven Wrapper and build files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

# Give execute permissions to the mvnw script
RUN chmod +x mvnw

# Build the application using Maven Wrapper
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-jammy

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/texasAPi-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application will run on
EXPOSE 8080

# Set environment variables (optional, can be overridden by Render or Docker)
ENV SPRING_PROFILES_ACTIVE=production

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]