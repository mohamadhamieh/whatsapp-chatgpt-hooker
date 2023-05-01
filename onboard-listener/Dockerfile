FROM openjdk:8

WORKDIR /app/

ENV BOOTSTRAP_SERVERS none

COPY ./build/libs/onboard-listener.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]
