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

==============================
ELK Client Service Server API
==============================

This page provides a static view of the methods in the elkclient server.  Please note that a
running ELK Client server provides a more useful version of this information.  View the details
at a URL like the following, but check the server configuration for the exact port number
(e.g., "9006") and context root (e.g., "elkclient") to use::

    http://localhost:9006/elkclient/swagger-ui.html
	
Backup & Restore
----------------

This section list features provided in this services.
    * Get all the indices present in elastic search.
    * Get all the repositories information present in elastic search.
    * Get all the snapshot present in elastic search.
    * Create elastic search repository.
    * Create elastic search snapshot.
    * Delete elastic search Indices.
    * Delete elastic search repository.
    * Delete elastic search snapshot.
    * Restore elastic search snapshot.
 
ELK Client APIs in Boreas Release
---------------------------------

This section lists the methods in version 2.2.4.

.. swaggerv2doc:: api/elkclient-api-docs-2.2.4.json

   