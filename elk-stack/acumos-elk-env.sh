#!/bin/bash
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
#
# What this is: Environment file for Acumos Kong API installation.
# Usage:
# - Intended to be called from docker-compose.yml
#

# Be verbose
set -x

export ACUMOS_ELK_ELASTICSEARCH_PORT=9200
export ACUMOS_ELK_NODE_PORT=9300
export ACUMOS_ELK_LOGSTASH_PORT=5000
export ACUMOS_ELK_KIBANA_PORT=5601

# Java heap size
export ACUMOS_ELK_ES_JAVA_HEAP_MIN_SIZE=1g
export ACUMOS_ELK_ES_JAVA_HEAP_MAX_SIZE=1g
export ACUMOS_ELK_LS_JAVA_HEAP_MIN_SIZE=1g
export ACUMOS_ELK_LS_JAVA_HEAP_MAX_SIZE=1g

# JDBC Parameters
host=$(ip route get 8.8.8.8 | awk '{print $NF; exit}')
export ACUMOS_ELK_JDBC_DRIVER_LIBRARY=/usr/share/logstash/vendor/bundle/mariadb-java-client-2.1.0.jar
export ACUMOS_ELK_JDBC_DRIVER_CLASS=org.mariadb.jdbc.Driver
export ACUMOS_ELK_DATABASE_PORT=3306
export ACUMOS_ELK_DATABASE_NAME=cds1140m_upd
export ACUMOS_ELK_DATABASE_URL=$host
export ACUMOS_ELK_JDBC_CONNECTION_STRING=jdbc:mariadb://$ACUMOS_ELK_DATABASE_URL:$ACUMOS_ELK_DATABASE_PORT/$ACUMOS_ELK_DATABASE_NAME
export ACUMOS_ELK_JDBC_USERNAME=cds_report
export ACUMOS_ELK_JDBC_PASSWORD=cds_report
