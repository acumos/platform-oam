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
