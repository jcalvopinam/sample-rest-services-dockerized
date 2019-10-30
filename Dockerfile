FROM openjdk:8-jdk-alpine

COPY target/sample-rest-services-dockerized-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xmx200m", "-jar", "/app/app.jar"]

EXPOSE 8080
