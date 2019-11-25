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







