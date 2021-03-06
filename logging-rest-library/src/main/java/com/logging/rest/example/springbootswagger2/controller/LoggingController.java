package com.logging.rest.example.springbootswagger2.controller;

import java.net.InetAddress;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.acumos.logging.util.ACUMOSLogConstants;
import org.acumos.logging.util.ACUMOSLogConstants.InvocationMode;
import org.acumos.logging.util.LogConfig;
import org.acumos.logging.util.LoggingConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
 
@Api(value = "Swagger2DemoRestController", description = "Looging Apis related to Logging jar!!!!")
@RestController
public class LoggingController {



	private static final Logger logger = LoggerFactory.getLogger(LoggingController.class);
	private ObjectMapper mapper = new ObjectMapper();
		
	

	@ApiOperation(value = "Get Logging publish GetInfo requests.")
	@RequestMapping(value = { "getInfo" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getInformation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		LogConfig.setEnteringMDCs("first", "second","","","");
		//LogConfig.setEnteringMDCs("elk-client", APINames.DEBUG_TEST,"test",LoggingConstant.MDC_INPROGRESS,LoggingConstant.MDC_INFO);

		
		logger.info(InvocationMode.SYNCHRONOUS.getmMarker(), "platform-oam");
		logger.debug("Inside getInfo Service");
		logger.info(InvocationMode.ASYNCHRONOUS.getmMarker(), "platform-oam");
		logger.info("Inside getInfo Service");

		logger.info(InvocationMode.SYNCHRONOUS.getmMarker(), "platform-oam");
		logger.debug("Inside getDebug Service");
		logger.info(ACUMOSLogConstants.MDCs.getREQUEST_ID());
		ACUMOSLogConstants.Markers.getINVOKE();
	
	
	
		logger.info(InvocationMode.SYNCHRONOUS.getmMarker(), "platform-oam");
		logger.info(ACUMOSLogConstants.ResponseStatus.COMPLETED.toString(), "platform-oam");
		ACUMOSLogConstants.ResponseStatus.COMPLETED.toString();
		
		logger.debug("Inside getInfo Service");
		logger.info(InvocationMode.ASYNCHRONOUS.getmMarker(), "platform-oam");
		logger.info(ACUMOSLogConstants.ResponseStatus.ERROR.toString(), "platform-oam");
		logger.info(ACUMOSLogConstants.ResponseStatus.INPROGRESS.toString(), "platform-oam");
		
		logger.info(ACUMOSLogConstants.ResponseSeverity.DEBUG.toString(), "platform-oam");
		logger.info(ACUMOSLogConstants.ResponseSeverity.ERROR.toString(), "platform-oam");
		logger.info(ACUMOSLogConstants.ResponseSeverity.FATAL.toString(), "platform-oam");
		logger.info(ACUMOSLogConstants.ResponseSeverity.INFO.toString(), "platform-oam");
		logger.info(ACUMOSLogConstants.ResponseSeverity.TRACE.toString(), "platform-oam");
		logger.info(ACUMOSLogConstants.MDCs.REQUEST_ID.toString(), "platform-oam");
		logger.info(ACUMOSLogConstants.MDCs.RESPONSE_DESCRIPTION.toString(), "platform-oam");
	
		logger.info("Inside getInfo Service");
		LogConfig.getHostDetails();
		LogConfig.clearMDCDetails();
		logger.info("method call ended.");
		logger.debug("method call ended.");

		return "";
	}
	
	@ApiOperation(value = "Get Logging publish debug requests.")
	@RequestMapping(value = { APINames.DEBUG_TEST }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getDebug(@PathVariable String markerInput, @PathVariable("markerInput1") String markerInput1, @PathVariable("user") String user) throws Exception {
		
		
		LogConfig.setEnteringMDCs("elk-client", APINames.DEBUG_TEST,user,LoggingConstant.MDC_COMPLETED,LoggingConstant.MDC_INFO);
	
		logger.info(InvocationMode.SYNCHRONOUS.getmMarker(), "platform-oam");
		MarkerFactory.getIMarkerFactory();
		logger.debug("Inside getDebug Service");
		String markerInputVal=markerInput;
		String markerInput1Val=markerInput1;
		
		logger.info(InvocationMode.SYNCHRONOUS.getmMarker(), "platform-oam");
		logger.info(InvocationMode.SYNCHRONOUS.getmMarker(), "platform-oam");
		
		logger.info(ACUMOSLogConstants.MDCs.ResponseSeverityMDC.MDC_DEBUG.toString());
	
		logger.info(InvocationMode.SYNCHRONOUS.getmMarker(), "platform-oam");
		
		MarkerFactory.getMarker(markerInputVal);
    
    	logger.error(MarkerFactory.getMarker(markerInputVal),
	        	  "This is a serious an User Input Marker error requiring the admin's attention",
	        	   new Exception("Just testing"));
    	
		logger.info(InvocationMode.ASYNCHRONOUS.getmMarker(), "platform-oam");
		logger.info("Inside getDebug Service");
		ACUMOSLogConstants.ResponseStatus.COMPLETED.toString();
		logger.info(ACUMOSLogConstants.ResponseSeverity.INFO.toString(), "platform-oam");
		logger.info("method call ended.");
		logger.debug("method call ended.");
		LogConfig.getHostDetails();
		LogConfig.clearMDCDetails();
	

		return "";
	}
	
	
	  @ApiOperation(value = "Gets MDC details.")
	    @RequestMapping(value = { APINames.GET_TEST }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseBody
	    public String getTest(@PathVariable("markerInput") String markerInput,@PathVariable("user") String user) {
	 
	        try {
	        	LogConfig.setEnteringMDCs("test", "test",user,LoggingConstant.MDC_ERROR,LoggingConstant.MDC_TRACE);
	        	String markerInputVal=markerInput;
	        	String marker="ENTRY";
	     
	        	Marker marker1=ACUMOSLogConstants.Markers.INVOKE_RETURN;
	        	
	        	MarkerFactory.getMarker(marker);
	        	System.out.println(MarkerFactory.getMarker(marker));
	        	
	        	MarkerFactory.getMarker(markerInputVal);
	     
	        	
	        	logger.info(marker1,"This is a confidential message....");
	        	
	        	Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
	        	logger.error(notifyAdmin,
	        	  "This is a serious an NOTIFY_ADMIN error requiring the admin's NOTIFY_ADMIN attention",
	        	   new Exception("Just testing"));
	        	
	        	//Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
	        	logger.error(MarkerFactory.getMarker(markerInputVal),
	        	  "This is a serious an User Input Marker error requiring the admin's attention",
	        	   new Exception("Just testing"));
	        	
	        	logger.info(InvocationMode.SYNCHRONOUS.getmMarker(), "platform-oam");
	    		logger.debug("Inside getWarn Service");
	    		LogConfig.getHostDetails();
	    		LogConfig.clearMDCDetails();
	    
	            }
	         catch (Exception e) {            
	           
	        }
	        return "";
	    }
	
	@ApiOperation(value = "Get Logging publish warn requests.")
	@RequestMapping(value = { "/warn" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getWarn() throws Exception {
		
	//	LogConfig.setEnteringMDCs("first", "second","test",LoggingConstant.MDC_INPROGRESS,LoggingConstant.MDC_ERROR);
		LogConfig.setEnteringMDCs("first", "second","","","");
		logger.info(InvocationMode.SYNCHRONOUS.getmMarker(), "platform-oam");
		logger.debug("Inside getWarn Service");
		logger.info(InvocationMode.ASYNCHRONOUS.getmMarker(), "platform-oam");
		logger.info("Inside getWarn Service");
		String marker="ENTRY";
    	Marker marker1=ACUMOSLogConstants.Markers.INVOKE_RETURN;
    	logger.error(marker1,
    	    	  "This is a serious an error requiring the admin's attention",
    	    	   new Exception("Just testing"));
    	logger.info(marker1,"This is a confidential message....");
    	logger.debug(marker1,"This is a confidential message....");
    	
    	MarkerFactory.getMarker(marker);
    	
    	Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
    	logger.error(notifyAdmin,
    	  "This is a serious an error requiring the admin's attention",
    	   new Exception("Just testing"));
   
		logger.info("method call ended.");
		logger.warn("method call ended.");
		LogConfig.getHostDetails();
		LogConfig.clearMDCDetails();

		return "";
	}
	
	
	
}
