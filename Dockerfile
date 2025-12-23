# Multi-stage build for optimized image size

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copy pom.xml and download dependencies (for better caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

# Add metadata
LABEL maintainer="kozionov"
LABEL description="Pathfinders - Electronic Journal Application"
LABEL version="0.1"

# Create non-root user for security
RUN addgroup -g 1000 pathfinders && \
    adduser -D -u 1000 -G pathfinders pathfinders

# Set working directory
WORKDIR /app

# Copy JAR from builder stage
COPY --from=builder /app/target/pathfinders-*.jar app.jar

# Create log directory
RUN mkdir -p /var/log/pathfinders && \
    chown -R pathfinders:pathfinders /var/log/pathfinders

# Switch to non-root user
USER pathfinders

# Expose application port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# JVM tuning for container environment
ENV JAVA_OPTS="-XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:InitialRAMPercentage=50.0 \
    -XX:+UseG1GC \
    -XX:+ExitOnOutOfMemoryError \
    -Djava.security.egd=file:/dev/./urandom"

# Run application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]