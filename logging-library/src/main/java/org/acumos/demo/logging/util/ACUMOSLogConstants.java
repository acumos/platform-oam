/*
  ===============LICENSE_START=======================================================
  Acumos
  ===================================================================================
  Copyright (C) 2019 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
  ===================================================================================
  This Acumos software file is distributed by AT&T and Tech Mahindra
  under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
   
	   http://www.apache.org/licenses/LICENSE-2.0
   
  This file is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===============LICENSE_END=========================================================
  */
package org.acumos.demo.logging.util;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.Properties;

import org.slf4j.MDC;

/**
 * Constants for standard ACUMOS headers, MDCs, etc.
 *
 */
public class ACUMOSLogConstants {

	/**
	 * Hide and forbid construction.
	 */

	private ACUMOSLogConstants() {

		throw new UnsupportedOperationException();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Inner classes.
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Marker constants.
	 */
	public static class Markers {

		/** Marker reporting invocation. */
		public static Marker INVOKE = MarkerFactory.getMarker(LoggingConstant.INVOKE);

		/** Marker reporting invocation. */
		public static Marker INVOKE_RETURN = MarkerFactory.getMarker(LoggingConstant.INVOKE_RETURN);

		/** Marker reporting synchronous invocation. */
		public static Marker INVOKE_SYNCHRONOUS = build(LoggingConstant.INVOKE, LoggingConstant.SYNCHRONOUS);

		/** Marker reporting asynchronous invocation. */
		public static Marker INVOKE_ASYNCHRONOUS = build(LoggingConstant.INVOKE, LoggingConstant.ASYNCHRONOUS);

		/** Marker reporting entry into a component. */
		public static Marker ENTRY = MarkerFactory.getMarker(LoggingConstant.ENTRY);

		/** Marker reporting exit from a component. */
		public static Marker EXIT = MarkerFactory.getMarker(LoggingConstant.EXIT);

		public static Marker getINVOKE() {
			return INVOKE;
		}

		public static void setINVOKE(Marker iNVOKE) {
			INVOKE = iNVOKE;
		}

		public static Marker getINVOKE_RETURN() {
			return INVOKE_RETURN;
		}

		public static void setINVOKE_RETURN(Marker iNVOKE_RETURN) {
			INVOKE_RETURN = iNVOKE_RETURN;
		}

		public static Marker getINVOKE_SYNCHRONOUS() {
			return INVOKE_SYNCHRONOUS;
		}

		public static void setINVOKE_SYNCHRONOUS(Marker iNVOKE_SYNCHRONOUS) {
			INVOKE_SYNCHRONOUS = iNVOKE_SYNCHRONOUS;
		}

		public static Marker getINVOKE_ASYNCHRONOUS() {
			return INVOKE_ASYNCHRONOUS;
		}

		public static void setINVOKE_ASYNCHRONOUS(Marker iNVOKE_ASYNCHRONOUS) {
			INVOKE_ASYNCHRONOUS = iNVOKE_ASYNCHRONOUS;
		}

		public static Marker getENTRY() {
			return ENTRY;
		}

		public static void setENTRY(Marker eNTRY) {
			ENTRY = eNTRY;
		}

		public static Marker getEXIT() {
			return EXIT;
		}

		public static void setEXIT(Marker eXIT) {
			EXIT = eXIT;
		}

		/**
		 * Build nested, detached marker.
		 * 
		 * @param m1
		 *            top token.
		 * @param m2
		 *            sub-token.
		 * @return detached Marker.
		 */
		private static Marker build(String m1, String m2) {
			Marker marker = MarkerFactory.getDetachedMarker(m1);
			marker.add(MarkerFactory.getDetachedMarker(m2));
			return marker;
		}

		/**
		 * Hide and forbid construction.
		 */
		private Markers() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * MDC name constants.
	 */
	public static class MDCs {

		public enum ResponseStatusMDC {

			MDC_COMPLETED, MDC_ERROR, MDC_INPROGRESS;

			/** Enum value. */

		}

		public enum ResponseSeverityMDC {

			
			/** Enum value. */

			MDC_INFO, MDC_ERROR, MDC_TRACE, MDC_DEBUG, MDC_WARN, MDC_FATAL;

			/** Enum value. */

		}

		// Tracing. ////////////////////////////////////////////////////////////

		/** MDC correlating messages for a logical transaction. */
		public static String REQUEST_ID = LoggingConstant.REQUEST_ID;

		/** MDC recording target service. */
		public static String TARGET_SERVICE_NAME = LoggingConstant.TARGET_SERVICE_NAME;

		/** MDC recording target entity. */
		public static String TARGET_ENTITY = LoggingConstant.TARGET_ENTITY;

		// Network. ////////////////////////////////////////////////////////////

		/** MDC recording caller address. */
		public static String CLIENT_IP_ADDRESS = LoggingConstant.CLIENT_IP_ADDRESS;

		/** MDC recording server address. */
		public static String SERVER_FQDN = LoggingConstant.SERVER_FQDN;

		/** MDC reporting outcome code. */
		public static String RESPONSE_CODE = LoggingConstant.RESPONSE_CODE;

		/** MDC reporting outcome description. */
		public static String RESPONSE_DESCRIPTION = LoggingConstant.RESPONSE_DESCRIPTION;

		/** MDC reporting outcome error level. */
		public static String RESPONSE_SEVERITY = LoggingConstant.RESPONSE_SEVERITY;

		/** MDC reporting outcome status of the request. */
		public static String STATUS_CODE = LoggingConstant.STATUS_CODE;

		public static String USER = LoggingConstant.USER;

		public static String getUSER() {
			return USER;
		}

		public static void setUSER(String uSER) {
			USER = uSER;
		}

		public static String getREQUEST_ID() {
			return REQUEST_ID;
		}

		public static void setREQUEST_ID(String rEQUEST_ID) {
			REQUEST_ID = rEQUEST_ID;
		}

		public static String getTARGET_SERVICE_NAME() {
			return TARGET_SERVICE_NAME;
		}

		public static void setTARGET_SERVICE_NAME(String tARGET_SERVICE_NAME) {
			TARGET_SERVICE_NAME = tARGET_SERVICE_NAME;
		}

		public static String getTARGET_ENTITY() {
			return TARGET_ENTITY;
		}

		public static void setTARGET_ENTITY(String tARGET_ENTITY) {
			TARGET_ENTITY = tARGET_ENTITY;
		}

		public static String getCLIENT_IP_ADDRESS() {
			return CLIENT_IP_ADDRESS;
		}

		public static void setCLIENT_IP_ADDRESS(String cLIENT_IP_ADDRESS) {
			CLIENT_IP_ADDRESS = cLIENT_IP_ADDRESS;
		}

		public static String getSERVER_FQDN() {
			return SERVER_FQDN;
		}

		public static void setSERVER_FQDN(String sERVER_FQDN) {
			SERVER_FQDN = sERVER_FQDN;
		}

		public static String getRESPONSE_CODE() {
			return RESPONSE_CODE;
		}

		public static void setRESPONSE_CODE(String rESPONSE_CODE) {
			RESPONSE_CODE = rESPONSE_CODE;
		}

		public static String getRESPONSE_DESCRIPTION() {
			return RESPONSE_DESCRIPTION;
		}

		public static void setRESPONSE_DESCRIPTION(String rESPONSE_DESCRIPTION) {
			RESPONSE_DESCRIPTION = rESPONSE_DESCRIPTION;
		}

		public static String getRESPONSE_SEVERITY() {
			return RESPONSE_SEVERITY;
		}

		public static void setRESPONSE_SEVERITY(String rESPONSE_SEVERITY) {
			RESPONSE_SEVERITY = rESPONSE_SEVERITY;
		}

		public static String getSTATUS_CODE() {
			return STATUS_CODE;
		}

		public static void setSTATUS_CODE(String sTATUS_CODE) {
			STATUS_CODE = sTATUS_CODE;
		}

		// Unsorted. ///////////////////////////////////////////////////////////

		/**
		 * Hide and forbid construction.
		 */
		private MDCs() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Header name constants.
	 */
	public static class Headers {

		/** HTTP X-ACUMOS-RequestID header. */
		public static String REQUEST_ID = "X-ACUMOS-RequestID";

		/**
		 * Hide and forbid construction.
		 */
		private Headers() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Overrideable method to set MDCs based on property values.
	 */
	public static void setDefaultMDCs() {
		MDC.put(MDCs.RESPONSE_SEVERITY, ResponseSeverity.INFO.toString());
		MDC.put(MDCs.STATUS_CODE, ResponseStatus.INPROGRESS.toString());
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Enums.
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Response success or not, for setting StatusCode.
	 */
	public enum ResponseStatus {

		COMPLETED, ERROR, INPROGRESS
	}

	/**
	 * Response of log level, for setting Severity.
	 */
	public enum ResponseSeverity {

		INFO, ERROR, TRACE, DEBUG, WARN, FATAL
	}

	/**
	 * Synchronous or asynchronous execution, for setting invocation marker.
	 */
	public enum InvocationMode {

		/** Synchronous, blocking. */
		SYNCHRONOUS(LoggingConstant.SYNCHRONOUS, Markers.INVOKE_SYNCHRONOUS),

		/** Asynchronous, non-blocking. */
		ASYNCHRONOUS(LoggingConstant.ASYNCHRONOUS, Markers.INVOKE_ASYNCHRONOUS);

		/** Enum value. */
		private String mString;

		/** Corresponding marker. */
		private Marker mMarker;

		public String getmString() {
			return mString;
		}

		public void setmString(String mString) {
			this.mString = mString;
		}

		public Marker getmMarker() {
			return this.mMarker;
		}

		public void setmMarker(Marker mMarker) {
			this.mMarker = mMarker;
		}

		/**
		 * Construct enum.
		 *
		 * @param s
		 *            enum value.
		 * @param m
		 *            corresponding Marker.
		 */
		InvocationMode(String s, Marker m) {
			this.mString = s;
			this.mMarker = m;
		}

		/**
		 * Get Marker for enum.
		 *
		 * @return Marker.
		 */

		@Override
		public String toString() {
			return this.mString;
		}
	}

}
