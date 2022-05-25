FROM maven:3.8.2-openjdk-17-slim as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml ./
COPY src ./src/

# Build a release artifact.
RUN mvn package -DskipTests

#FROM adoptopenjdk/openjdk8:jdk8u202-b08-alpine-slim
FROM openjdk:17-alpine

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/user-service-*.jar /user-service.jar

# Run the web service on container startup.
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=${PORT}","-jar","/user-service.jar"]