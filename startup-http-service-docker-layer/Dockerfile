FROM adoptopenjdk:8-jre-openj9

USER root

ENV HTTP_SERVICE_HOME /usr/http-service
ENV HTTP_SERVICE_LOGS /var/log/http-service
ENV HTTP_SERVICE_USER http-svc
ENV HTTP_SERVICE_GROUP http-svc
ENV HTTP_SERVICE_ARTIFACT_NAME http-service.jar
ENV JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1

ENV TZ America/Mexico_City
ENV LANG es_MX.UTF-8
ENV LANGUAGE es_MX.UTF-8
ENV LC_ALL es_MX.UTF-8

RUN apt-get update \
	&& apt-get install -y --no-install-recommends tzdata \
	&& locale-gen es_MX.UTF-8 && update-locale \
	&& addgroup --system --gid 1000 ${HTTP_SERVICE_GROUP} && adduser --system --uid 1000 ${HTTP_SERVICE_USER} --ingroup ${HTTP_SERVICE_GROUP} \ 
	&& mkdir -p ${HTTP_SERVICE_HOME} \
	&& chown -R ${HTTP_SERVICE_USER} ${HTTP_SERVICE_HOME} \
	&& chgrp -R ${HTTP_SERVICE_GROUP} ${HTTP_SERVICE_HOME} \
	&& mkdir -p ${HTTP_SERVICE_LOGS} \
	&& chown -R ${HTTP_SERVICE_USER} ${HTTP_SERVICE_LOGS} \
	&& chgrp -R ${HTTP_SERVICE_GROUP} ${HTTP_SERVICE_LOGS}

VOLUME ${HTTP_SERVICE_LOGS}

WORKDIR ${HTTP_SERVICE_HOME}
USER ${HTTP_SERVICE_USER}

ENTRYPOINT java ${JAVA_OPTS} ${JAVA_OPTS_MEM} -jar ${HTTP_SERVICE_HOME}/${HTTP_SERVICE_ARTIFACT_NAME} ${SPRING_OPTS}
