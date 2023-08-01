FROM gradle:8.1.1-jdk11 as builder

WORKDIR /build

COPY greendev/*.gradle ./
RUN gradle build --parallel --continue

COPY greendev/* ./
COPY /config/application.yml ./src/main/resources/application.yml
RUN gradle build --parallel --continue

FROM openjdk:11-slim

WORKDIR /app

COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./greendev.jar

EXPOSE 8080
CMD ["java", "-jar", "greendev.jar"]
