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

export ACUMOS_ELK_API_ELASTICSEARCH_API_LISTENS_PORT=9200
export ACUMOS_ELK_API_ELASTICSEARCH_NODE_LISTENS_PORT=9300
export ACUMOS_ELK_API_LOGSTASH_PORT=5000
export ACUMOS_ELK_API_KIBANA_PORT=7006

# Java heap size
export ACUMOS_ELK_API_JAVA_HEAP_SIZE=4g

