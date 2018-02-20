# https://hub.docker.com/r/prima/filebeat/
FROM docker.elastic.co/beats/filebeat:6.0.1
COPY /config/filebeat.yml /usr/share/filebeat/filebeat.yml
USER root
RUN chown filebeat /usr/share/filebeat/filebeat.yml && chmod go-w /usr/share/filebeat/filebeat.yml
USER filebeat
