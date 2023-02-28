FROM openjdk:11

COPY target/TransferMoney-0.0.1-SNAPSHOT.jar app.jar
COPY /src/main/resources/ /src/main/resources/

EXPOSE 5500

ENTRYPOINT ["java", "-jar", "app.jar"]