FROM openjdk:17-jdk
COPY form/images/CV form/images/CV
COPY target/hehexd-0.0.1-SNAPSHOT.jar .
EXPOSE 7070
CMD ["java", "-jar", "hehexd-0.0.1-SNAPSHOT.jar"]