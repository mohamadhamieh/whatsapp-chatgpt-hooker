FROM openjdk:8

WORKDIR /app/

COPY ./build/libs/kafka-huroku-listener-1.0-SNAPSHOT.jar /app/app.jar

ENV CALLBACK_URL none
ENV BOOTSTRAP_SERVERS_CONFIG none

CMD ["java","-jar","app.jar"]