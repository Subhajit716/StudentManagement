# ---------- Stage 1: build the jar with Maven ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# The cache mount keeps downloaded dependencies between builds so rebuilds are fast.
# Tests are skipped here because they would need a running MySQL.
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests clean package

# ---------- Stage 2: small runtime image with only Java + the jar ----------
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
