FROM maven:3.6-openjdk-17-slim AS build
WORKDIR /app

# COPY pom.xml ke dalam direktori kerja container
COPY pom.xml .

# COPY seluruh direktori src
COPY src ./src

# Sekarang, saat pom.xml ada di tempat yang benar, mvn clean package dapat dijalankan
RUN mvn clean package -DskipTests && rm -rf /app/src /app/pom.xml

FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copy artifact .jar yang dibangun dari stage build ke direktori kerja container
COPY --from=build /app/target/enigma-coop-0.0.1-SNAPSHOT.jar /app/enigma-coop-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/app/enigma-coop-0.0.1-SNAPSHOT.jar"]
