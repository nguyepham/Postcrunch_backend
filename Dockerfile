FROM bellsoft/liberica-openjdk-alpine:17
LABEL authors="nguyepham"
WORKDIR /app
COPY target/backend-0.0.1-SNAPSHOT.jar /app/postcrunch.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/postcrunch.jar"]