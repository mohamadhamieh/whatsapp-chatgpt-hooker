FROM openjdk:8

WORKDIR /app/

COPY ./build/libs/chatgpt-listener.jar /app/app.jar

ENV BOOTSTRAP_SERVERS none
ENV BEARER none
ENV OPENAI_URL none

CMD ["java", "-jar", "app.jar"]