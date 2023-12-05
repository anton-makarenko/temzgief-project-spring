FROM mysql:8.2.0
LABEL authors="makar"

ENV MYSQL_DATABASE=shopdb2
ENV MYSQL_ROOT_PASSWORD=root

ADD project2.sql /docker-entrypoint-initdb.d

EXPOSE 3306