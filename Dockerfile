FROM amazoncorretto:17.0.3

RUN mkdir /opt/temzgief

COPY target/temzgief-project-spring-1.0-SNAPSHOT.jar /opt/temzgief

EXPOSE 8443

ENTRYPOINT ["java", "-jar", "/opt/temzgief/temzgief-project-spring-1.0-SNAPSHOT.jar"]