FROM openjdk:8-jre-alpine
ARG JAR_FILE

USER root

ENV SPRING_BOOT_HOME /usr/spring-boot-services 
ENV	SPRING_BOOT_USER sbapp 
ENV	SPRING_BOOT_GROUP sbapp 
ENV	SPRING_BOOT_ARTIFACT_NAME sb-app.jar 
ENV	JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 \
	-Dlogging.path=/var/log/sb-app/ -Dsun.net.inetaddr.ttl=60 -Djava.net.preferIPv4Stack=true

ENV TZ America/Mexico_City
ENV LANG es_MX.UTF-8
ENV LANGUAGE es_MX.UTF-8
ENV LC_ALL es_MX.UTF-8

COPY ${JAR_FILE} ${SPRING_BOOT_HOME}/${SPRING_BOOT_ARTIFACT_NAME}

VOLUME /tmp

RUN apk add --update --no-cache bash tzdata \
	&& addgroup -S $SPRING_BOOT_USER \
	&& adduser -S -g $SPRING_BOOT_GROUP $SPRING_BOOT_USER

WORKDIR $SPRING_BOOT_HOME
USER $SPRING_BOOT_USER

ENTRYPOINT java $JAVA_OPTS ${JAVA_OPTS_MEM} -jar $SPRING_BOOT_HOME/$SPRING_BOOT_ARTIFACT_NAME ${SPRING_OPTS}