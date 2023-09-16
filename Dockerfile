FROM openjdk:17
LABEL maintainer="Leandro Santos <leandro.lucas_@hotmail.com>"

ARG JAR_FILE=./cars-api/target/cars-api.jar
ARG WEB_APP=./cars-web/dist/cars-web/*

ARG APP_PORT=80

ENV SERVER_PORT=${APP_PORT}

COPY ${JAR_FILE} app.jar
COPY ${WEB_APP} static/

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE ${APP_PORT}
