FROM openjdk:17-jdk
COPY target/hehexd-0.0.1-SNAPSHOT.jar .
EXPOSE 7070
CMD ["java", "-jar", "hehexd-0.0.1-SNAPSHOT.jar"]