#FROM openjdk:17-alpine
#VOLUME /temp
#COPY build/libs/backend-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom -Xms256m -Xmx512m", "-jar", "/app.jar"]

FROM maven:3.8.3-openjdk-17 AS builder
WORKDIR /backend
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests
FROM openjdk:17-alpine
WORKDIR /backend
COPY --from=builder /backend/target/backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]

