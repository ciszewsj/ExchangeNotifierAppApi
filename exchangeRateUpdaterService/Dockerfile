FROM openjdk:17

WORKDIR /app

COPY target/exchange-rate-updater-server.jar /app/exchange-rate-updater-server.jar
COPY src/main/resources/application-docker.properties /app/application.properties
COPY src/main/resources/exchangenotifierapp-firebase-adminsdk-wi2m8-d26e9ef104.json /app/exchangenotifierapp-firebase-adminsdk-wi2m8-d26e9ef104.json

CMD ["java", "-jar", "exchange-rate-updater-server.jar"]

