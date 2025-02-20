FROM amazoncorretto:21-alpine-jdk

ENV APP_PROFILE=docker

RUN mkdir -p /var/log/spring-security && \
    adduser -S -s /bin/bash spring-sec_user

USER spring-sec_user

WORKDIR /home/spring-security

COPY target/*.jar .

RUN find . -name *.jar -exec ln -s {} spring-sec.jar \;

EXPOSE 8000

VOLUME /var/log/spring-sec

ENTRYPOINT ["java", "-Dspring.profiles.active=${APP_PROFILE}", "-jar", "spring-sec.jar"]
