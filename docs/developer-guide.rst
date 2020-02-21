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

==========================================================================
Platform Operations, Administration, and Management (OA&M) Developer Guide
==========================================================================


The OA&M project is for the processes, activities, tools, and standards involved with operating, administering, managing and maintaining the Acumos platform. The project uses Docker, Docker Compose, shell scripts, and third-party tools such as the Kong API and Elastic Stack.

Jira: `https://jira.acumos.org <https://jira.acumos.org>`_   project name: platform-oam

Gerrit:  git clone https://gerrit.acumos.org/r/platform-oam


==================================
Elastic Stack Developer Guide
==================================


Acumos ELK stack setup has five main components:

- **Elastic Search**: Elastic search is a distributed open source search engine based on Apache Lucene. Acumos Elastic search stores all the logs and metrics of Acumos platform host. 
- **Logstash**: Logstash is a data pipeline that helps collect, parse, and analyze a large variety of incoming logs generated across Acumos Platform. 
- **Kibana**: Web interface for searching and visualizing logs.
- **Filebeat**: Filebeat serves as a log shipping agent, Installed on Acumos platform servers it sends logs to Logstash.
- **Metricbeat**: Installed on Acumos platform servers. it periodically collects the metrics from the Acumos platform host operating system which includes running components information  and ships them to elasticsearch. These metrics are used for monitoring.

==================================
ELK-CLient Developer Guide
==================================
What is ELK-CLient?
=======================
ELASTIC SEARCH
============================
In this Java Project there is one config folder under this folder there is a file elastic search.yml which contains the docker 
cluster and other details to run the elastic search component.
There is file in the project directory called as DockerFile in this file we give the configuration information related to
the elastic search is given,like version of the elastic search used.


LOGSTASH
=============================
In this Java Project there is one config folder under this folder there is a file logstash.yml which contains the docker 
cluster and other details to run the elastic search component.
There is file in the project directory called as DockerFile in this file we give the configuration information related to
the logstah like logstash & maria db version is given.

KIBANA
=============================
In this Java Project there is one config folder under this folder there is a file kibana.yml which contains the docker 
cluster and other details to run the elastic search component.
There is file in the project directory called as DockerFile in this file we give the configuration information related to
the kibana version command to run the Kibana.yml is given.


METRIC BEAT
=============================
In this Java Project there is one config folder under this folder there is a file metricbeat.yml which contains all the
setup related details to run the metric beat components.
There is a file in the project directory called as DockerFile in this file we give the configuration information related to
the elastic search is given,like version of the metric beat used,command to run the metricbeat.yml 

ELK-CLient
=============================
In this project we have created several Rest API to work on the elastic search.Every API has its own functionality.
Given below Rest API
ElasticSearchServiceController
Apart from the REST API there are the two services files also there SnapshotServiceImpl(Implementation of operation related 
to elastic stack snapshot.).Another one is the SnapshotRepositoryServiceImpl( Implementation of operation related to elastic stack repository.)



==========================================
elk client rest api ,request and responses
==========================================

ELK Stack Client Back end APIs
========================

- Get all the archive snapshot
- Get all the indices of Elastic stack
- Get all the Elastic search repositories details of Elastic stack
- Get all the Elastic search snapshot
- Archive and Restore elastic stack snapshot
- Create Elastic stack repository
- Create Elastic stack snapshot
- Delete Elastic stack Indices
- Delete Elastic stack repository
- Delete Elastic stack snapshot
- Restore Elastic stack snapshot.

==============================================
ELK client REST API urls,request and responses
==============================================

1.Get all the archive snapshot.
==============================
We use this rest API method to retrieve all the archived snapshots in elastic search.

``http://cognita-dev1-logcollector.eastus.cloudapp.azure.com:9600/elkclient/swagger-ui.html#/elastic-search-service-controller/all/archive``

Request Body Example:

.. code-block:: json

    {
		{
  "archiveInfo": [
    {
      "date": "string",
      "repositoryName": "string",
      "snapshots": [
        {
          "endTime": "2019-03-28 08-53-41",
          "indices": "metricbeat-6.2.4-2019.04.04",
          "snapShotId": "snapshot-2019-03-28t08-53-41",
          "startTime": "2019-03-28 08-53-41",
          "state": "SUCCESS",
          "status": "Snapshot creation is in progress. Will take some time due size of data' or 'OK"
        }
      ]
    }
  ],
  "msg": "string",
  "status": "string"
}	
	}
	
Response Body Example:

.. code-block:: json

    {
  "archiveInfo": [
    {
      "date": "2019-10-28:22:52:57Z",
      "repositoryName": "28Oct",
      "snapshots": [
        {
          "snapShotId": "28bkup",
          "status": "OK",
          "state": "SUCCESS",
          "startTime": "2019-10-28 22:52:44",
          "endTime": "2019-10-28 22:52:49",
          "indices": [
            "metricbeat-6.2.4-2019.10.26",
            "logstash"
          ]
        }
      ]
    },
    {
      "date": "2019-11-01:06:38:47Z",
      "repositoryName": "abc1234",
      "snapshots": [
        {
          "snapShotId": "abc",
          "status": "OK",
          "state": "SUCCESS",
          "startTime": "2019-11-01 06:30:35",
          "endTime": "2019-11-01 06:32:37",
          "indices": [
            "testdb"
          ]
        },
        {
          "snapShotId": "abc1",
          "status": "OK",
          "state": "SUCCESS",
          "startTime": "2019-11-01 06:38:43",
          "endTime": "2019-11-01 06:38:47",
          "indices": [
            "metricbeat-6.2.4-2019.10.26"
          ]
        }
      ]
    }
  ],
  "msg": "Action:INFO done",
  "status": "success"
}

2. Get all the indices of Elastic stack.
=======================================
We use this rest API method to get all the indices of Elastic stack in elastic search.

``http://cognita-dev1-logcollector.eastus.cloudapp.azure.com:9600/elkclient/swagger-ui.html#/elastic-search-service-controller/all/indices``


Request Body Example:

.. code-block:: json

    {
		{
  "indices": [
    "string"
  ]
}
	}
	
Response Body Example:

.. code-block:: json

    {
  "indices": [
    "metricbeat-6.2.4-2019.10.27",
    "metricbeat-6.2.4-2019.10.25",
    "metricbeat-6.2.4-2019.10.29",
    "metricbeat-6.2.4-2019.11.05",
    "logstash",
    "metricbeat-6.2.4-2019.11.02",
    "metricbeat-6.2.4-2019.10.31",
    "metricbeat-6.2.4-2019.11.04",
    "metricbeat-6.2.4-2019.11.03",
    "metricbeat-6.2.4-2019.11.06",
    "testdb",
    "metricbeat-6.2.4-2019.10.30",
    "metricbeat-6.2.4-2019.11.01",
    "metricbeat-6.2.4-2019.10.28",
    "metricbeat-6.2.4-2019.10.24",
    "metricbeat-6.2.4-2019.10.26"
  ]
}

3.Get all the elastic search repositories details of Elastic stack.
===================================================================
We use this rest API method to retrieve  all the repositories in elastic search.


``http://cognita-dev1-logcollector.eastus.cloudapp.azure.com:9600/elkclient/swagger-ui.html#/elastic-search-service-controller/all/repositories``

Request Body Example:

.. code-block:: json

    {
		{
  "repositories": [
    {
      "name": "logstash",
      "settings": {
        "additionalProp1": {},
        "additionalProp2": {},
        "additionalProp3": {}
      },
      "type": "fs"
    }
  ]
}
	}
	
Response Body Example:

.. code-block:: json

   {
  "repositories": [
    {
      "name": "28Oct",
      "type": "fs",
      "settings": {
        "compress": "true",
        "location": "28Oct"
      }
    },
    {
      "name": "abc1234",
      "type": "fs",
      "settings": {
        "compress": "true",
        "location": "abc1234"
      }
    },
    {
      "name": "logstash",
      "type": "fs",
      "settings": {
        "compress": "true",
        "location": "logstash"
      }
    }
  ]
}

4.Get all the elastic search snapshot.
=====================================

We use this rest API method to retrieve all the snapshots from the repository in elastic search.


``http://cognita-dev1-logcollector.eastus.cloudapp.azure.com:9600/elkclient/swagger-ui.html#/elastic-search-service-controller/all/snapshot``

Request Body Example:

.. code-block:: json

    {
		{
  "elasticsearchSnapshots": [
    {
      "repositoryName": "logstash",
      "snapshots": [
        {
          "endTime": "2019-03-28 08-53-41",
          "indices": "metricbeat-6.2.4-2019.04.04",
          "snapShotId": "snapshot-2019-03-28t08-53-41",
          "startTime": "2019-03-28 08-53-41",
          "state": "SUCCESS",
          "status": "Snapshot creation is in progress. Will take some time due size of data' or 'OK"
        }
      ]
    }
  ]
}
	}
	
Response Body Example:

.. code-block:: json

    {
  "elasticsearchSnapshots": []
}

5.Archive and Restore elastic stack snapshot.
============================================
We use this rest API method to Archive and Restore elastic stack snapshot from elastic search.

``http://cognita-dev1-logcollector.eastus.cloudapp.azure.com:9600/elkclient/swagger-ui.html#/elastic-search-service-controller/archive/action``

Request Body Example:

.. code-block:: json

    {
		{
  "action": "archive/restore/delete",
  "repositoryName": [
    "string"
  ]
}
	}

Response Body Example:

.. code-block:: json

    {
  "archiveInfo": null,
  "msg": "",
  "status": "fail"
	}

6.Create Elastic stack repository.
=================================

We use this rest API method to create a new repository in elastic search.

``http://cognita-dev1-logcollector.eastus.cloudapp.azure.com:9600/elkclient/swagger-ui.html#/elastic-search-service-controller/create/repositories``

Request Body Example:

.. code-block:: json

    {
		{
  "nodeTimeout": "string",
  "repositoryName": "logstash"
}
	}
	
Response Body Example:

.. code-block:: json

    {
	can't parse JSON.  Raw result:

	false | RepositoryName already exist
	}

7.Create elastic stack snapshot.
================================
We use this rest API method to create a new snapshot in a repository in elastic search.

``http://cognita-dev1-logcollector.eastus.cloudapp.azure.com:9600/elkclient/swagger-ui.html#/elastic-search-service-controller/create/snapshot``

Request Body Example:

.. code-block:: json

    {	
		{
  "createSnapshots": [
    {
      "indices": [
        "string"
      ],
      "repositoryName": "string",
      "snapshotName": "string"
    }
  ],
  "nodeTimeout": 1
}
	}
	
Response Body Example:

.. code-block:: json

    {
  "timestamp": "2019-11-06T13:28:01.986+0000",
  "message": "Elasticsearch exception [type=index_not_found_exception, reason=no such index]",
  "details": "uri=/elkclient/create/snapshot"
}

8.Delete elastic stack Indices.
===============================

We use this rest API method to delete the indices from a repository in elastic search.

``http://cognita-dev1-logcollector.eastus.cloudapp.azure.com:9600/elkclient/swagger-ui.html#/elastic-search-service-controller/delete/indices``

Request Body Example:

.. code-block:: json

    {
	{
  "indices": [
    "string"
  ]
}
	}
	
Response Body Example:

.. code-block:: json

    
	{
  "timestamp": "2019-11-06T13:25:38.500+0000",
  "message": "Elasticsearch exception [type=index_not_found_exception, reason=no such index]",
  "details": "uri=/elkclient/delete/indices"
	}	


9.Delete Elastic stack repository.
=================================

We use this rest API method to delete the repository from a repository in elastic search.

``http://cognita-dev1-logcollector.eastus.cloudapp.azure.com:9600/elkclient/swagger-ui.html#/elastic-search-service-controller/delete/repositories``

Request Body Example:

.. code-block:: json

    {
		{
  "nodeTimeout": "string",
  "repositoryName": "logstash"
}
	}

Response Body Example:

.. code-block:: json

    {
  "timestamp": "2019-11-06T13:25:06.117+0000",
  "message": "failed to parse setting [DeleteRepositoryRequest.masterNodeTimeout] with value [string] as a time value: unit is missing or unrecognized",
  "details": "uri=/elkclient/delete/repositories"
	}	

	
10.Delete elastic stack snapshot.
================================
We use this rest API method to delete a snapshot from a repository in elastic search.

``http://cognita-dev1-logcollector.eastus.cloudapp.azure.com:9600/elkclient/swagger-ui.html#/elastic-search-service-controller/delete/snapshot``

Request Body Example:

.. code-block:: json

    {
	{
  "deleteSnapshots": [
    {
      "repositoryName": "logstash",
      "snapShotId": "snapshot-2019-03-28t08-53-41"
    }
  ],
  "nodeTimeout": 1
}
	}
	
Response Body Example:

.. code-block:: json
    
	{
  "timestamp": "2019-11-06T13:24:24.664+0000",
  "message": "Elasticsearch exception [type=snapshot_missing_exception, reason=[logstash:snapshot-2019-03-28t08-53-41] is missing]",
  "details": "uri=/elkclient/delete/snapshot"
	}

11.Restore elastic stack snapshot.
=================================
We use this rest API method to restore the snapshot from a repository in elastic search.

``http://cognita-dev1-logcollector.eastus.cloudapp.azure.com:9600/elkclient/swagger-ui.html#/elastic-search-service-controller/restore/snapshot``

Request Body Example:

.. code-block:: json

    {
	{
  "nodeTimeout": 1,
  "repositoryName": "logstash",
  "restoreSnapshots": [
    {
      "snapshotName": "string"
    }
  ]
}
	}
	
Response Body Example:

.. code-block:: json

    	{
  "timestamp": "2019-11-06T13:20:08.023+0000",
  "message": "Elasticsearch exception [type=snapshot_restore_exception, reason=[logstash:string] snapshot does not exist]",
  "details": "uri=/elkclient/restore/snapshot"
	}


==================================
Logging Library Developer Guide
==================================
What is Logging Library?
=======================

Logging Library has the following given below features :

There are thress .java(ACUMOSLogConstants,LoggingConstant.java and LogConfig.java) files.

1.In the ACUMOSLogConstants.java following  given below features are there.
Marker
====================
Marker is a spacial class  has the given below features.

Java logging frameworks allow you to filter log messages based on the logger name and the message log level.You can tag your
log messages with user-defined markers in order to filter them later on.

Markers are named objects used to enrich log statements. Conforming logging system Implementations of SLF4J determine how
information conveyed by markers are used, if at all. In particular, many conforming logging systems ignore marker data.
For this example, we will be using Logback as logger with SLF4J.Logback was conceived and created as a successor to Log4J.
Logback supports markers for the logging calls. These Markers allow association of tags with log statements. 

Marker has following given below attributes as INVOKE,INVOKE_RETURN,INVOKE_SYNCHRONOUS,INVOKE_ASYNCHRONOUS,ENTRY & EXIT
,User can select any of the attribute as per his choice.

MDC
=====

The MDC manages contextual information on a per thread basis. Typically, while starting to service a new client request,
the developer will insert pertinent contextual information, such as the client id, client's IP address, request parameters
etc. into the MDC.Logback components, if appropriately configured, will automatically include this information in each log
entry.

MDC in Log4j allows us to fill a map-like structure with pieces of information that are accessible to the appender when 
the log message is actually written.The MDC structure is internally attached to the executing thread in the same way a 
ThreadLocal variable would be.

And so, the high level idea is:to fill the MDC with pieces of information that we want to make available to the appender
then log a message and finally, clear the MDC.

Most server applications need to handle multiple clients simultaneously. Typically, the server application allocates a 
separate thread to handle a single client request. In such a system different threads handle different client requests 
in parallel and the log messages written by the threads interleave. In order to differentiate log messages from different
threads from each other a diagnostic context comes in handy.Diagnostic context is a map associated with a particular thread.
Each thread maintains its own map. You can store arbitrary key-value pairs in the map and in turn lay out your log messages 
to include the values from the map.

MDC has following given below attributes as REQUEST_ID,TARGET_SERVICE_NAME,TARGET_ENTITY,CLIENT_IP_ADDRESS,SERVER_FQDN,
RESPONSE_CODE,RESPONSE_DESCRIPTION,RESPONSE_SEVERITY & STATUS_CODE,User can select any of the attribute as per his choice.

We have two (1- MDC, and 2 - MDCs )
New MDCs are added to serve more better way for ResponseStatusMDC and ResponseSeverityMDC of MDCs.
It will be useful in logging the thread requests/responses.
For more information we have added thread specific ResponseStatusMDC  which has three attributes as  MDC_COMPLETED,  MDC_ERROR 
&  MDC_INPROGRESS, user can select one of these attributes in logging statements like below:
public enum ResponseStatusMDC {
                     
                     MDC_COMPLETED,
                     MDC_ERROR,
                     MDC_INPROGRESS
}
Also for thread specific MDC had added ResponseSeverityMDC  which has six attributes as MDC_INFO,  MDC_ERROR,  MDC_TRACE,
 MDC_DEBUG, MDC_WARN, MDC_FATAL.user can select one of these attributes in logging statements.
  public enum ResponseSeverityMDC
{    MDC_INFO,    MDC_ERROR,    MDC_TRACE,    MDC_DEBUG,    MDC_WARN,    MDC_FATAL   }

Implementation of MDC 
=====================
How MDC are called externally from other project through the method setEnteringMDCs in LogConfig.java.
MDC is used for thread specific request so we just call this method setEnteringMDCs().

		MDC.put(MDCs.REQUEST_ID, requestId);		
		MDC.put(MDCs.TARGET_ENTITY, targetEntry);
		MDC.put(MDCs.TARGET_SERVICE_NAME, targetService);
		MDC.put(MDCs.CLIENT_IP_ADDRESS, ip);
		MDC.put(MDCs.SERVER_FQDN, hostname);
		MDC.put(MDCs.USER, user);

In this method setEnteringMDCs() we write the given below lines for the specific thread.

Also whatever parameters we pass as responseCode & responseSeverity in
setEnteringMDCs(String targetEntry,String targetService,String user,String responseCode,String responseSeverity) method.

There are conditions according to whatever ResponseStatusMDC & ResponseSeverityMDC you want to implement in logging statements
that will be printed in your logging statements with the help of given below statements as :

For example if you pass responseCode as MDC_COMPLETED then  given below lines will be printed in your logging statements.
MDC.put(MDCs.RESPONSE_DESCRIPTION, MDCs.ResponseStatusMDC.MDC_COMPLETED.toString());

And For example if you pass responseSeverity as MDC_INFO then  given below lines will be printed in your logging statements.
MDC.put(MDCs.RESPONSE_SEVERITY, MDCs.ResponseSeverityMDC.MDC_INFO.toString());
	

Header
=======
In this class there is one attribute as REQUEST_ID whose value is X-ACUMOS-RequestID.

ResponseStatus enumeration
==========================
In this enumeration there are three types of Response Status as COMPLETED,ERROR,INPROGRESS.The end user can choose any of the
response attribute as per his choice.

ResponseSeverity enumeration:
=============================
In this enumeration there are five types of Response Severity is given as :INFO,ERROR,TRACE,DEBUG,WARN,FATAL.The end user can
choose any of the response severity attribute as per his choice.

InvocationMode enumeration
==========================
Invocation mode can be SYNCHRONOUS or ASYNCHRONOUS as per the user requirement.

2.Another File is the LogConfig.java
====================================
In this file there is a static method as LogConfig.setEnteringMDCs(String targetEntry,String targetService,String user,String responseCode,String responseSeverity)
The user puts the entries in HashMap in the given below format.

		MDC.put(MDCs.REQUEST_ID, requestId);		
		MDC.put(MDCs.TARGET_ENTITY, targetEntry);
		MDC.put(MDCs.TARGET_SERVICE_NAME, targetService);
		MDC.put(MDCs.CLIENT_IP_ADDRESS, ip);
		MDC.put(MDCs.SERVER_FQDN, hostname);
		MDC.put(MDCs.USER, user);
Where responseCode & responseSeverity the user will pass whatever thread specific ResponseStatusMDC	& ResponseSeverityMDC he wants 
to implement in the logging statements.	
		MDC.put(MDCs.RESPONSE_DESCRIPTION, MDCs.ResponseStatusMDC.MDC_COMPLETED.toString());
		MDC.put(MDCs.RESPONSE_SEVERITY, MDCs.ResponseSeverityMDC.MDC_INFO.toString());
		
Here the targetEntry is the maven project module name for example in maven project elk-client the targetEntry name is
elk-client.

Here the targetEntry is end point url of the rest api method which we want to access.For example to fetch all the indices of 
elastic search we define end point url of the reat api as /all/indices in the ElkClientConstants.GET_ALL_INDICES and
define GET_ALL_INDICES whose value is /all/indices in the ElkClientConstants.java file. 

Here the user is the who login into the web application and accessing the particular maven project module.

3.Last File is the LoggingConstant.java
=======================================

All the variables used in the logging-library is kept here as a constant or utility reusable file.

================================================
Testing Logging Library Developer Guide
================================================
What is Logging Library Testing Rest API?
================================================
1.Rest API Test.
=============================
We have created logging-rest-library project only for the developers to test the logging-library in local,logging-rest-library will not be deployed on the server.
logging-rest-library is meant only for the developers to test logging-library in their system locally & will not be deployed or can not be accessed on any servers.
In this project we are importing the logging-library jar so we will import all the functionality & various features of the logging-library project through the logging-library jar.
We have created some Rest API methods in the test project logging-rest-library in that we are implementing the different different
features of the logging-library project.

How to implement the Logging Library jar?
========================================= 

To implement the Logging Library jar,there are some few specific given below guidelines which the developer should use while
implementing the logging-library jar.

1.In the starting of the implemented REST API method first use the line from the Logging Library jar as 
 LogConfig.setEnteringMDCs(String targetEntry, String targetService, String user, String responseCode,String responseSeverity)

Where the targetEntry is your maven module name,targetService is the REST API url of the exposed method,user is who has login
into the system,responseCode is the ResponseStatusMDC and responseSeverity is the ResponseSeverityMDC,you can choose any values
out of the values given in the ResponseStatusMDC & ResponseSeverityMDC.

2.Then use the particular log levels like debug,error,info,fatal,warn etc whatever you want to implement in your logging statements.

3.Suppose you want to enrich the logs with some particular Marker then for this first initialize the MarkerFactory.getIMarkerFactory();
Then use the line as logger.error(MarkerFactory.getMarker(markerInputVal), "This is a serious an User Input Marker error requiring the admin's attention",new Exception("Just testing")); 
where markerInputVal is the particular marker which you want to use in your application.

4.In the end of the implemented REST API method use the line LogConfig.clearMDCDetails() to clear all the log MDC details.

Logback.xml
=============
We have defined various appenders while help in creating the log statements.With the help of these appenders we can print
the logs as per user requirement.

Steps to include logging-library.jar in your project.
==================================================

Logging Library is provided in the form of as a jar,Suppose we want to add this logging-library.jar to a new project then given
below are the steps to in guide this jar and use in your project.

1.Add the given below entry in dependency section of the pom.xml of your new project.
		<dependency>
			<groupId>org.acumos.platform-oam</groupId>
			<artifactId>logging-library</artifactId>
			<version>3.0.4-SNAPSHOT</version>
		</dependency>
		
2.Publish the logging-library.jar into the maven repository.
		
3.Now Suppose you want to add logging related statements in your java files then just you need to write the logger.Debug_levels
as per your requirement, like debug,error,fatal,info,warn.

4.Whatever logging functionality you want in your logging statements as per your requirement,you can just import from the 
classes files of the jar.


You will import the appropriate,required  and use it your project as per the end user requirement.To see how you can use the
logging-library library ,you can refer to the above section Logging Library Developer Guide.







