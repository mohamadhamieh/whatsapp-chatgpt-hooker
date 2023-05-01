FROM openjdk:8

WORKDIR /app/

COPY ./build/libs/kafka-topics-creation-0.0.1-SNAPSHOT.jar /app/app.jar

ENV BOOTSTRAP_SERVERS none

CMD ["java", "-jar", "app.jar"]