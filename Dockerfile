FROM openjdk:17
LABEL maintainer="Leandro Santos <leandro.lucas_@hotmail.com>"

ARG JAR_FILE=./cars-api/target/cars-api.jar
ARG APP_PROPERTIES=./cars-api/src/main/resources/*
ARG APP_PORT=80

ENV SERVER_PORT=${APP_PORT}

COPY ${JAR_FILE} app.jar
COPY ${APP_PROPERTIES} config/
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE ${APP_PORT}
