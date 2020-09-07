FROM alpine

ENV TZ America/Mexico_City
ENV LANG es_MX.UTF-8
ENV LANGUAGE es_MX.UTF-8
ENV LC_ALL es_MX.UTF-8

# Filebeat
ENV FILEBEAT_VERSION=7.9.0
ENV FILEBEAT_HOME /usr/share/filebeat 
ENV	FILEBEAT_USER filebeat 
ENV	FILEBEAT_GROUP filebeat 

ARG SHA=a069cc0906ff95d1c42118d501edc81d8af10d77124e5b38d209201d49e6649e4e2130a270885131abda214fee72c60f0d0174ef9ba92617ecc5b2554bc2fc35
ARG BASE_URL=https://artifacts.elastic.co/downloads/beats/filebeat


RUN apk --no-cache --update upgrade && apk --no-cache add libc6-compat curl tar tzdata \
    && addgroup -S ${FILEBEAT_GROUP} && adduser -S ${FILEBEAT_USER} -G ${FILEBEAT_GROUP} \
    && mkdir -p ${FILEBEAT_HOME} \
    && curl -fsSL -o /tmp/source-file.tar.gz ${BASE_URL}/filebeat-${FILEBEAT_VERSION}-linux-x86_64.tar.gz \
    && echo "${SHA}  /tmp/source-file.tar.gz" | sha512sum -c - \
    && tar -xzf /tmp/source-file.tar.gz -C ${FILEBEAT_HOME} --strip-components=1 \
    && rm -f /tmp/source-file.tar.gz \
    && chown -R ${FILEBEAT_USER} ${FILEBEAT_HOME} \
    && chgrp -R ${FILEBEAT_GROUP} ${FILEBEAT_HOME} \
    && ln -s ${FILEBEAT_HOME}/filebeat /usr/bin/filebeat

COPY filebeat.yml /usr/share/filebeat/filebeat.yml

WORKDIR $FILEBEAT_HOME
USER $FILEBEAT_USER

ENTRYPOINT filebeat -c ${FILEBEAT_HOME}/filebeat.yml -e