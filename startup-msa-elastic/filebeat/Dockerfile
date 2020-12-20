FROM alpine

ENV TZ America/Mexico_City
ENV LANG es_MX.UTF-8
ENV LANGUAGE es_MX.UTF-8
ENV LC_ALL es_MX.UTF-8

# Filebeat
ENV FILEBEAT_VERSION 7.10.1
ENV FILEBEAT_HOME /usr/share/filebeat 
ENV FILEBEAT_CONFIG /etc/filebeat
ENV	FILEBEAT_USER filebeat 
ENV	FILEBEAT_GROUP filebeat 

ARG SHA=1d18b491bea723f49c8ab07dcd1ccc7512610ab3a04b4bc1718b380d76bcf055f6f17eb346d0084bded08e5246ddbc660654c4e8757d287181fdd74ffb63f7b4
ARG BASE_URL=https://artifacts.elastic.co/downloads/beats/filebeat

RUN apk --no-cache --update upgrade && apk --no-cache add libc6-compat curl tar tzdata \
    && addgroup -S ${FILEBEAT_GROUP} && adduser -S ${FILEBEAT_USER} -G ${FILEBEAT_GROUP} \
    && mkdir -p ${FILEBEAT_HOME} \
    && mkdir -p ${FILEBEAT_CONFIG} \
    && curl -fsSL -o /tmp/source-file.tar.gz ${BASE_URL}/filebeat-${FILEBEAT_VERSION}-linux-x86_64.tar.gz \
    && echo "${SHA}  /tmp/source-file.tar.gz" | sha512sum -c - \
    && tar -xzf /tmp/source-file.tar.gz -C ${FILEBEAT_HOME} --strip-components=1 \
    && rm -f /tmp/source-file.tar.gz \
    && chown -R ${FILEBEAT_USER} ${FILEBEAT_HOME} \
    && chgrp -R ${FILEBEAT_GROUP} ${FILEBEAT_HOME} \
    && ln -s ${FILEBEAT_HOME}/filebeat /usr/bin/filebeat

WORKDIR ${FILEBEAT_HOME}
USER ${FILEBEAT_USER}

ENTRYPOINT filebeat -c ${FILEBEAT_CONFIG}/filebeat.yml -e -strict.perms=false