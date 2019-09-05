FROM adoptopenjdk/openjdk11:alpine-slim
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} publicCarRental-backend.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/publicCarRental-backend.jar"]