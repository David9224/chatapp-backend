FROM openjdk:17-jdk-slim-buster

MAINTAINER David Aristizabal <davidaristi-9224@hotmail.com>

# Add a volume pointing to /tmp
VOLUME /tmp

# The application's jar file
ARG JAR_FILE=build/libs/chatapp-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} chatapp.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/chatapp.jar"]