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
#FROM docker.elastic.co/logstash/logstash:6.0.1

# Add your logstash plugins setup here
# Example: RUN logstash-plugin install logstash-filter-json
# Install plugin for development
##RUN /opt/logstash/bin/logstash-plugin install --development
##RUN /opt/logstash/bin/logstash-plugin list


# Prepare directory & file for test
##USER root
##RUN mkdir -p /test

#ADD spec /test/spec
#ADD pipeline /test/pipeline
##ADD spec/*.rb /test/spec
##ADD pipeline/logstash.conf /test/pipeline/logstash.conf
##ADD run.sh /usr/local/bin/run.sh

##RUN chown logstash /usr/local/bin/run.sh && chmod +x /usr/local/bin/run.sh

##USER logstash
##CMD ["/usr/local/bin/run.sh"]
#USER logstash
