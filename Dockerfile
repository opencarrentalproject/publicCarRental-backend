FROM openjdk:11-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} publicCarRental-backend.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/publicCarRental-backend.jar"]