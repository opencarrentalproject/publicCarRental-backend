FROM adoptopenjdk/openjdk11:alpine-slim
VOLUME /tmp
COPY target/publicCarRental-backend.jar publicCarRental-backend.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/publicCarRental-backend.jar"]