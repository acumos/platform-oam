.. ===============LICENSE_START=======================================================
.. Acumos
.. ===================================================================================
.. Copyright (C) 2017-2018 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
.. ===================================================================================
.. This Acumos documentation file is distributed by AT&T and Tech Mahindra
.. under the Creative Commons Attribution 4.0 International License (the "License");
.. you may not use this file except in compliance with the License.
.. You may obtain a copy of the License at
..  
..      http://creativecommons.org/licenses/by/4.0
..  
.. This file is distributed on an "AS IS" BASIS,
.. WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
.. See the License for the specific language governing permissions and
.. limitations under the License.
.. ===============LICENSE_END=========================================================

=====================================================================
Platform Operations, Administration, and Management (OA&M) User Guide
=====================================================================

Operations, Administration and Management/Maintenance are the processes, activities, tools, and standards involved with operating, administering, managing and maintaining any system. 

Acumos Elastic Stack for Log Analytics
======================================
One of the functions of (OA&M) for the Acumos platform is to collect and correlate log files from the other platform components in order to support debugging, metrics, alarms, etc. for development and operations purposes. These metrics can reveal issues and potential risks so administrators can take corrective action. To this end, the OA&M component has defined a logging standard to be used by all of those components in order to support correlation. OA&M uses the `Elasticsearch, Logstack, Kibana stack <https://www.elastic.co/elk-stack>`_  and `Filebeat <https://www.elastic.co/products/beats/filebeat>`_ to collect and centralize logs that are generated via the microservices.  This guide that describes how to use the Acumos Elastic Stack (formerly known as the ELK Stack).

Target Users
------------
Acumos Platform super admins


Assumptions
-----------
All the modules are following the Acumos Logging Guidelines


Elastic Stack Architecture
--------------------------

.. image:: images/elk_stack.png

Elastic Stack Component Versions
--------------------------------

- elasticsearch 5.5.1
- kibana:5.5.1
- logstash:5.5.1
- filebeat:6.0.1
- metricbeat:6.2.4

Elastic Stack Setup
-------------------
Elastic Stack installation is automated with Docker Compose. Installation is done on a server separate from where Acumos has been installed.

Prerequisites
-------------
`Docker <https://docs.docker.com/>`_ and `Docker Compose <https://docs.docker.com/compose/install/>`_ installed


Steps
-----

1. Clone the platform-oam repository 

.. code:: bash

   $ git clone https://gerrit.acumos.org/r/platform-oam

2. Create docker volume namely acumos-esdata
   
.. code:: bash

   $ docker volume create acumos-esdata


3. The docker-compose.yaml file as well as component directories are located in the elk-stack directory. Each component has a Dockerfile. You need to build the docker-compose file if you are using it for the first time or if you have made changed any Dockerfile or the contents of its build directory.

.. code:: bash

   $ cd elk-stack

   $ ./docker-compose-elk.sh build


4. Builds, (re)creates, starts, and attaches to containers for Elasticsearch, Logstash, Kibana

.. code:: bash

   $ ./docker-compose-elk.sh up -d


5. To stop the running containers without removing them

.. code:: bash

   $ ./docker-compose-elk.sh stop


Filebeat setup steps:
---------------------
Filebeat should be installed as an agent on the servers on which Acumos is running.
Add the configuration below to the docker-compose where the Acumos is installed.  

.. code:: yaml

   filebeat:
       container_name: filebeat
       image: <filebeat-image-name>
       volumes:
         - <volume-name>:/filebeat-logs
       environment:
         - LOGSTASH_HOST=<elk-stack-host-hostname>
		 - LOGSTASH_PORT=5000


Metricbeat setup steps:
-----------------------
Metricbeat should be installed as an agent on the servers on which Acumos is running.
Add the configuration below to the docker-compose where the Acumos is installed. 

.. code:: yaml

   metricbeat:
       image: <metricbeat-image-name>
       network_mode: host
       volumes:
       #Mount the docker, filesystem to enable Metricbeat to monitor the host rather than the Metricbeat container.
         - /proc:/hostfs/proc:ro
         - /sys/fs/cgroup:/hostfs/sys/fs/cgroup:ro
         - /:/hostfs:ro
         - /var/run:/var/run:rw
         - /var/run/docker.sock:/var/run/docker.sock
       command: metricbeat -e -strict.perms=false -system.hostfs=/hostfs
       environment:
         - SHIPPER_NAME=DOCKY
         - ELASTICSEARCH_HOST=<elk-stack-host-hostname>
         - ELASTICSEARCH_PORT=9200
         - PROCS=.*
         - PERIOD=10s
         - SHIPPER_NAME=super-app
 
 
Adding a New Log
----------------
Filebeat docker is a customized image that depends on filebeat.yml, a configuration layer. 
For adding new log under prospectors of filebeat.yml, need to add log location path as it is in <volume-name>.

.. code:: yaml

   filebeat.prospectors:
     - input_type: log
       paths:
         - /filebeat-logs/portal-be/*.log


Elastic Stack UI Tour
---------------------
According to the `Kibana website <https://www.elastic.co/guide/en/kibana/current/introduction.html>`_, Kibana is an open source analytics and visualization platform designed to work with Elasticsearch. You use Kibana to search, view, and interact with data stored in Elasticsearch indices. You can easily perform advanced data analysis and visualize your data in a variety of charts, tables, and maps.
Kibana makes it easy to understand large volumes of data. Its simple, browser-based interface enables you to quickly create queries in real time.

For more details visit `Kibana User Guide <https://www.elastic.co/guide/en/kibana/5.5/index.html/>`_.

Site admins have access to Elastic Stack's Kibana Dashboard. Login to the dashboard:

		.. image:: images/acumos_Sign_In.JPG

Go to SITE ADMIN -> Monitoring and click on **Login to Dashboard** in the USERS section

		.. image:: images/acumos_site_admin.jpg


Redirects to Loading Kibana visualization platform

		.. image:: images/loadingKibana.jpg



Acumos Kibana Dashboard Creation
--------------------------------

The Kibana dashboard is used to view all the saved Visualizations.

To create dashboard click on Create a dashboard or On plus sign show in the search bar.

.. image:: images/kibana_dashboard_1.jpg

click on Visit the Visualize app

.. image:: images/kibana_dashboard_2.jpg

click on "Create a visualization" or "+"(i.e Plus sign) show in the search bar.

.. image:: images/kibana_visualization_1.jpg

Select visualization type. For example click on "Pie".

.. image:: images/kibana_visualization_2.jpg

Choose search source as ``logstash-*``

.. image:: images/kibana_visualization_3.jpg

Click on Split Slices

.. image:: images/kibana_visualization_4.jpg

Select Aggregation as "Terms" and Field as "userAgent.keyword", Click on "Apply changes"

Note: Elasticsearch aggregations are to extract and process your data.

.. image:: images/kibana_visualization_5.jpg

To save this chart click on "Save", Enter a name appropriate name. For example "Acumos User Login".
 
.. image:: images/kibana_visualization_6.jpg

Click on "Dashboard", On the below screen visualization namely "Acumos User Login"  is appearing. For select this visualization click on "+" (i.e. plus sign) show in the search bar.

.. image:: images/kibana_dashboard_3.jpg

Click on "Add" button, to add the visualization.

.. image:: images/kibana_dashboard_4.jpg

Select the visualization for example here we have visualization namely "Acumos User Login".

.. image:: images/kibana_dashboard_6.jpg

Click on "Save" button. Enter a name appropriate name. For example "Acumos User Login".

.. image:: images/kibana_dashboard_7.jpg

Click on "Dashboard", On the below screen created dashboard can be viewed namely "Acumos User Login".

.. image:: images/kibana_dashboard_8.jpg

Acumos Kibana Dashboard Save
----------------------------

Click on "Management", On the below screen click on save object.

.. image:: images/kibana_save_dashboard_1.JPG


Click on "Export Everything" to export the dashboard and "Import" to import the saved dashboard.

.. image:: images/kibana_save_dashboard_2.JPG

.. note::

    export/import document should be in JSON format.

An example JSON file that can be used to import a Dashboard is available in the platform-oam repo, `elk-stack directory <https://gerrit.acumos.org/r/gitweb?p=platform-oam.git;a=tree;f=elk-stack;hb=refs/heads/master>`_.