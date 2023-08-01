FROM openjdk:11-slim as builder

WORKDIR /build

COPY greendev/ ./
COPY config/application.yml ./src/main/resources/application.yml
RUN ./gradlew build

FROM openjdk:11-slim

WORKDIR /app

COPY --from=builder /build/greendev/build/libs/*-SNAPSHOT.jar ./greendev.jar

EXPOSE 8080
CMD ["java", "-jar", "greendev.jar"]
