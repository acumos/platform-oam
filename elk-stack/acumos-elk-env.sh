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
# What this is: Environment file for ELK installation.
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
export ACUMOS_ELK_ES_JAVA_HEAP_MIN_SIZE=4g
export ACUMOS_ELK_ES_JAVA_HEAP_MAX_SIZE=4g
export ACUMOS_ELK_LS_JAVA_HEAP_MIN_SIZE=4g
export ACUMOS_ELK_LS_JAVA_HEAP_MAX_SIZE=4g

#Below elk-stack-host-hostname needs to be updated with the log collector VM
export ACUMOS_ELK_HOST=elk-stack-host-hostname

# Should ONLY use Staging, if Release version not available or compatible
export NEXUS3_STAGING_REGISTRY_LF=nexus3.acumos.org:10004

# Should ONLY use Release version
export NEXUS3_RELEASE_REGISTRY_LF=nexus3.acumos.org:10002

# Images
export ELASTICSEARCH_IMAGE=acumos-elasticsearch:2.0.0
export LOGSTASH_IMAGE=acumos-logstash:2.0.0
export KIBANA_IMAGE=acumos-kibana:2.0.0
