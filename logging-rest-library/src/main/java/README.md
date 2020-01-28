
==================================
Logging Library Developer Guide
==================================
What is Logging Library?
=======================

Logging Library has the following given below features :

There are two .java(ACUMOSLogConstants and LogConfig.java) files and one logback.xml file is there.

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

3.Logback.xml
=============
We have defined various appenders while help in creating the log statements.With the help of these appenders we can print
the logs as per user requirement.

================================================
Testing Logging Library Developer Guide
================================================
What is Logging Library Testing Rest API?
================================================
1.Rest API Test.
=============================
We have created logging-rest-demo project, in this project we are importing the logging-demo jar so we will import all the
functionality & various features of the logging-demo project through the logging-demo jar.
We have created some Rest API methods in the test project logging-rest-demo in that we are implementing the different different
features of the logging-demo project.

How to implement the Logging Library jar?
========================================= 

To implement the Logging Library jar,there are some few specific given below guidelines which the developer should use while
implementing the logging-demo jar.

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

Steps to include logging-demo.jar in your project.
==================================================

Logging Library is provided in the form of as a jar,Suppose we want to add this logging-demo.jar to a new project then given
below are the steps to in guide this jar and use in your project.

1.Add the given below entry in dependency section of the pom.xml of your new project.
		<dependency>
			<groupId>org.acumos.platform-oam</groupId>
			<artifactId>logging-demo</artifactId>
			<version>3.0.4-SNAPSHOT</version>
		</dependency>
		
2.Publish the logging-demo.jar into the maven repository.
		
3.Now Suppose you want to add logging related statements in your java files then just you need to write the logger.Debug_levels
as per your requirement, like debug,error,fatal,info,warn.

4.Whatever logging functionality you want in your logging statements as per your requirement,you can just import from the 
classes files of the jar.


You will import the appropriate,required  and use it your project as per the end user requirement.To see how you can use the
logging-demo library ,you can refer to the above section Logging Library Developer Guide.







