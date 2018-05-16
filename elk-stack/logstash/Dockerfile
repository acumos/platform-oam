# ===============LICENSE_START=======================================================
# Acumos Apache-2.0
# ===================================================================================
# Copyright (C) 2017-2018 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
# ===================================================================================
# This Acumos software file is distributed by AT&T and Tech Mahindra
# under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# This file is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ===============LICENSE_END=========================================================
# https://github.com/elastic/logstash-docker
FROM docker.elastic.co/logstash/logstash:5.5.1
ENV ACUMOS_ELK_JDBC_DRIVER_LIBRARY=/usr/share/logstash/vendor/bundle/mariadb-java-client-2.1.0.jar
ENV ACUMOS_ELK_JDBC_DRIVER_CLASS=org.mariadb.jdbc.Driver
ENV ACUMOS_ELK_DATABASE_PORT=3306
ENV ACUMOS_ELK_DATABASE_NAME=databasename
ENV ACUMOS_ELK_DATABASE_URL=databaseurl
ENV ACUMOS_ELK_JDBC_CONNECTION_STRING=jdbc:mariadb://$ACUMOS_ELK_DATABASE_URL:$ACUMOS_ELK_DATABASE_PORT/$ACUMOS_ELK_DATABASE_NAME
ENV ACUMOS_ELK_JDBC_USERNAME=username
ENV ACUMOS_ELK_JDBC_PASSWORD=password
RUN /usr/share/logstash/bin/logstash-plugin install logstash-input-jdbc
ADD https://downloads.mariadb.com/enterprise/1prc-8jnh/connectors/java/connector-java-2.1.0/mariadb-java-client-2.1.0.jar /usr/share/logstash/vendor/bundle/
USER root
RUN ls -al /usr/share/logstash/vendor/bundle \
  && chmod a+r /usr/share/logstash/vendor/bundle/mariadb-java-client-2.1.0.jar

