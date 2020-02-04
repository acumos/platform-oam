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
package org.acumos.logging.util;

import java.net.InetAddress;
import java.util.Properties;
import java.util.UUID;

import org.acumos.logging.util.ACUMOSLogConstants.MDCs;
import org.acumos.logging.util.ACUMOSLogConstants.ResponseStatus;
import org.slf4j.MDC;

public class LogConfig {

	public static void setEnteringMDCs(String targetEntry, String targetService, String user, String responseCode,
			String responseSeverity) throws Exception {
		String hostname = "";
		String ip = "";

		ACUMOSLogConstants.setDefaultMDCs();
		String requestId = UUID.randomUUID().toString();
		String finalHostIP = getHostDetails();
		if (finalHostIP != null) {
			String hostIpArr[] = finalHostIP.split("/");
			hostname = hostIpArr[0];
			ip = hostIpArr[1];
		}
		MDC.put(MDCs.REQUEST_ID, requestId);
		MDC.put(MDCs.TARGET_ENTITY, targetEntry);
		MDC.put(MDCs.TARGET_SERVICE_NAME, targetService);
		MDC.put(MDCs.CLIENT_IP_ADDRESS, ip);
		MDC.put(MDCs.SERVER_FQDN, hostname);

		if (user != null && !user.isEmpty()) {
			MDC.put(MDCs.USER, user);
		}

		if (responseCode != null && !responseCode.isEmpty()) {
			if (responseCode.equalsIgnoreCase(LoggingConstant.MDC_COMPLETED)) {
				MDC.put(MDCs.RESPONSE_DESCRIPTION, MDCs.ResponseStatusMDC.MDC_COMPLETED.toString());
			} else if (responseCode.equalsIgnoreCase(LoggingConstant.MDC_ERROR)) {
				MDC.put(MDCs.RESPONSE_DESCRIPTION, MDCs.ResponseStatusMDC.MDC_ERROR.toString());
			} else if (responseCode.equalsIgnoreCase(LoggingConstant.MDC_INPROGRESS)) {
				MDC.put(MDCs.RESPONSE_DESCRIPTION, MDCs.ResponseStatusMDC.MDC_INPROGRESS.toString());
			}
		}

		if (responseSeverity != null && !responseSeverity.isEmpty()) {
			if (responseSeverity.equalsIgnoreCase(LoggingConstant.MDC_INFO)) {
				MDC.put(MDCs.RESPONSE_SEVERITY, MDCs.ResponseSeverityMDC.MDC_INFO.toString());
			} else if (responseSeverity.equalsIgnoreCase(LoggingConstant.MDC_ERROR)) {
				MDC.put(MDCs.RESPONSE_SEVERITY, MDCs.ResponseSeverityMDC.MDC_ERROR.toString());
			} else if (responseSeverity.equalsIgnoreCase(LoggingConstant.MDC_TRACE)) {
				MDC.put(MDCs.RESPONSE_SEVERITY, MDCs.ResponseSeverityMDC.MDC_TRACE.toString());
			} else if (responseSeverity.equalsIgnoreCase(LoggingConstant.MDC_DEBUG)) {
				MDC.put(MDCs.RESPONSE_SEVERITY, MDCs.ResponseSeverityMDC.MDC_DEBUG.toString());
			} else if (responseSeverity.equalsIgnoreCase(LoggingConstant.MDC_WARN)) {
				MDC.put(MDCs.RESPONSE_SEVERITY, MDCs.ResponseSeverityMDC.MDC_WARN.toString());
			} else if (responseSeverity.equalsIgnoreCase(LoggingConstant.MDC_FATAL)) {
				MDC.put(MDCs.RESPONSE_SEVERITY, MDCs.ResponseSeverityMDC.MDC_TRACE.toString());
			}
		}

	}

	public static void clearMDCDetails() {
		MDC.clear();
	}

	public static String getHostDetails() throws Exception {
		InetAddress ip;
		String hostname = null;
		String ipStr = null;
		String finalHostIP = null;

		ip = InetAddress.getLocalHost();
		hostname = ip.getHostName();
		ipStr = ip.getHostAddress();
		if (hostname != null && ipStr != null) {
			finalHostIP = hostname + "/" + ipStr;
		}
		return finalHostIP;
	}
}
