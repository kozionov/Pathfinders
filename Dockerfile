FROM openjdk:17
RUN mkdir -p /app/
WORKDIR /app/
COPY target/pathfinders-0.1.jar /app/pathfinders.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/pathfinders.jar"]
