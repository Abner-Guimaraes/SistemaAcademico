FROM maven:3.9.7-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Compila o projeto e gera o arquivo .jar
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
# Copia o JAR do estágio de build
COPY --from=build /app/target/academic-system-1.0-SNAPSHOT.jar /app/academic-system.jar
# Como é uma aplicação que interage via terminal na versão CLI,
# usaremos a Main tradicional (sem JavaFX) para facilitar a execução no Docker.
ENTRYPOINT ["java", "-cp", "/app/academic-system.jar", "org.example.Main"]
