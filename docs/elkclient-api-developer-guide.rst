.. ===============LICENSE_START=======================================================
.. Acumos CC-BY-4.0
.. ===================================================================================
.. Copyright (C) 2019 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
.. ===================================================================================
.. This Acumos documentation file is distributed by AT&T and Tech Mahindra
.. under the Creative Commons Attribution 4.0 International License (the "License");
.. you may not use this file except in compliance with the License.
.. You may obtain a copy of the License at
..
.. http://creativecommons.org/licenses/by/4.0
..
.. This file is distributed on an "AS IS" BASIS,
.. WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
.. See the License for the specific language governing permissions and
.. limitations under the License.
.. ===============LICENSE_END=========================================================

==============================================
Developer Guide for the Elastic Client Service 
==============================================

This microservice provides Backup & Restore services to ELK components in the
Acumos machine-learning platform. It is built using the Spring-Boot platform.
This document primarily offers guidance for server developers.

Supported Methods and Objects
-----------------------------

The microservice endpoints and objects are documented using Swagger. A running
server documents itself at a URL like the following, but consult the server's
configuration for the exact port number (e.g., "9006") and context root
(e.g., "elkclient") to use::

    http://localhost:9006/elkclient/swagger-ui.html

Building and Packaging
----------------------

As of this writing the build (continuous integration) process is fully automated
in the Linux Foundation system using Gerrit and Jenkins. This section describes
how to perform local builds for development and testing.

Prerequisites
~~~~~~~~~~~~~

The build machine needs the following:

1. Java version 1.8
2. Maven version 3
3. Connectivity to Maven Central to download required jars

Use maven to build and package the service into a single "fat" jar using this
command::

    mvn clean install

Development and Local Testing
-----------------------------

This section provides information for running the server in a
production/development environment, assuming that the application is packaged
into a docker container for deployment.

Prerequisites
~~~~~~~~~~~~~

    1. Java version 1.8 in the runtime environment; i.e., installed in the
       docker container
    2. The username/password combination to access the database
    3. The Nexus username/password combination to access.

Configuring the system
~~~~~~~~~~~~~~~~~~~~~~

At runtime in production deployments, in addition to using a configuration file,
environment-specific configuration properties should be supplied using a block of
JSON in an environment variable called SPRING\_APPLICATION\_JSON. This can easily
be done using the deployment templates. The default SV Scanning templates
for use with docker-compose.

.. code:: bash

   # Get the platform-oam repository 
   $ git clone https://gerrit.acumos.org/r/platform-oam
   # Select the Boreas branch
   cd platform-oam
   git checkout boreas
   # See what environment configuration options are supported
   cat platform-oam/acumos-elk-env.sh
   # See the docker-compose deployment template with references to options
   cat platform-oam/docker-compose.yml
	
Launch Instructions
~~~~~~~~~~~~~~~~~~~

To run the elkclient service in a local docker environment:

1. Build an image locally or use an image in the Acumos Nexus repositories.

2. Update environment variables as referenced by the template, either
   directly or in acumos-elk-env.sh:

   * ACUMOS_ELK_HOST: hostname Acumos elasticsearch.
   * ELK_CLIENT_CRONSCHEDULE_CREATESNAPSHOT_TIME: Elk client schedule cron job for snapshot creation
   
2. Use the docker-compose process that applies to your environment, e.g.

   * for a standalone docker container::

    docker-compose up -d elk-client
	