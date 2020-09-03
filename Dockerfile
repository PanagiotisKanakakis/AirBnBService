#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
MAINTAINER milefanouris
COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package -Dspring.profiles.active=prod

#
# Package stage
#
FROM adoptopenjdk/openjdk11:latest
MAINTAINER milefanouris

COPY --from=build /home/app/target/airbnb-server.jar /home/app/airbnb-server.jar
RUN chown -R  app:app /home/app

EXPOSE 8080
ENTRYPOINT ["/home/app/run.sh"]
