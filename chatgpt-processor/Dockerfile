FROM openjdk:8

WORKDIR /app/

COPY ./build/libs/chatgpt-processor.jar /app/app.jar

ENV BOOTSTRAP_SERVERS none

CMD ["java","-jar","app.jar"]