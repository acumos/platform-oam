.. ===============LICENSE_START=======================================================
.. Acumos
.. ===================================================================================
.. Copyright (C) 2019 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
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

========================================================================
Platform Operations, Administration, and Management (OA&M) Release Notes
========================================================================

Version 3.0.3, 03 September 2019
--------------------------------
* Repository Name in the GET all archive API (`ACUMOS-3378 <https://jira.acumos.org/browse/ACUMOS-3378>`_)
* Delete snapshot API (`ACUMOS-3379 <https://jira.acumos.org/browse/ACUMOS-3379>`_)
* OA & M - Java Code Upgrade to Java 11 or 12 (`ACUMOS-3325 <https://jira.acumos.org/browse/ACUMOS-3325>`_)

Version 3.0.2, 21 August 2019
-------------------------------
* Create Snapshot API (`ACUMOS-3347 <https://jira.acumos.org/browse/ACUMOS-3347>`_)

Version 3.0.1, 20 August 2019
-------------------------------
* Archival API is required (`ACUMOS-3301 <https://jira.acumos.org/browse/ACUMOS-3301>`_)
* Platform maintenance support - archival (`ACUMOS-2704 <https://jira.acumos.org/browse/ACUMOS-2704>`_)
* placeholder (`ACUMOS-2705 <https://jira.acumos.org/browse/ACUMOS-2705>`_)
* OA&M backlog (`ACUMOS-2703 <https://jira.acumos.org/browse/ACUMOS-2703>`_)

Version 2.2.4, 01 August 2019
-------------------------------
* Update docs for elk-client (`ACUMOS-3308 <https://jira.acumos.org/browse/ACUMOS-3308>`_)

Version 2.2.3, 11 July 2019
-------------------------------
* Platform maintenance support - archival (`ACUMOS-2704 <https://jira.acumos.org/browse/ACUMOS-2704>`_)

Version 2.2.2, 18 April 2019
-------------------------------
* Metricbeat index archival/purging (`ACUMOS-2065 <https://jira.acumos.org/browse/ACUMOS-2065>`_)
* Platform maintenance support (`ACUMOS-2004 <https://jira.acumos.org/browse/ACUMOS-2004>`_)
* Acumos Platform > Logstash to send model usage logs to "model-usage-logs" index instead of "logstash" index (`ACUMOS-2686 <https://jira.acumos.org/browse/ACUMOS-2686>`_)

Version 2.2.1, 17 April 2019
-------------------------------
* Update logstash queries for CDS version 2.2.1 (`ACUMOS-2765 <https://jira.acumos.org/browse/ACUMOS-2765>`_)

Version 2.0.9, 16 April 2019
-------------------------------
* IST- elastic search fails to run (`ACUMOS-2747 <https://jira.acumos.org/browse/ACUMOS-2747>`_)

Version 2.0.8, 29 March 2019
-------------------------------
* Add implementation for Platform maintenance support (`ACUMOS-2343 <https://jira.acumos.org/browse/ACUMOS-2343>`_)
* Add implementation for Platform maintenance support - backend (`ACUMOS-2344 <https://jira.acumos.org/browse/ACUMOS-2344>`_)

Version 2.0.7, 07 February 2019
-------------------------------
* Add implementation for mandatory MDC default value in logging POC and bump ELK stack version to 2.0.7(`ACUMOS-2458 <https://jira.acumos.org/browse/ACUMOS-2458>`_)

Version 2.0.4, 24 January 2019
-------------------------------
* Update logstash queries for database version 2.0.4 (`ACUMOS-2403 <https://jira.acumos.org/browse/ACUMOS-2403>`_)

Version 2.0.0, 18 December 2018
-------------------------------
* kibana dashboard verbose logstash logs are crashing the log server (`ACUMOS-2151 <https://jira.acumos.org/browse/ACUMOS-2151>`_)
* Upgrade ELK ,Filebeat, Metricbeat to version 6.x (`ACUMOS-1999 <https://jira.acumos.org/browse/ACUMOS-1999>`_)

Version 1.18.2, 1 October 2018
------------------------------

-  Updated ELK, filebeat and metricbeat image version to match with CDS 1.18.2 version (ACUMOS-1808).

Version 1.18.1, 14 September 2018
---------------------------------

-  Updated ELK, filebeat and metricbeat as per standard log specification (ACUMOS-1091).

Version 1.18.0, 5 September 2018
--------------------------------

-  Updated ELK, filebeat and metricbeat image version to match with database 1.18 version (ACUMOS-1695).

Version 1.2.0, 15 August 2018
-----------------------------

-  Updated ELK queries to match with database 1.17 version

Version 1.1.0, 27 June 2018
---------------------------

-  Increasing the reload interval for metricbeat

Version�1.0.0,�7�May�2018
-------------------------

- Increase the ES,LS memory size and made it configurable (ACUMOS-669)
- Added docker volume to persist acumos elasticsearch data (ACUMOS-669)
- Added Metricbeat setup, it collects metrics of CPU, Memory, docker container information of acumos platform (ACUMOS-669)
- Updated Elastic Stack installation steps

Version 0.1, 15 February 2018
-----------------------------

-  Elastic Stack installation and documentation
