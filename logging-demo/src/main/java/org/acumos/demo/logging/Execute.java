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
package org.acumos.demo.logging;

import java.util.UUID;
import org.acumos.demo.logging.service.ApplicationService;
import org.acumos.demo.logging.util.ACUMOSLogConstants.MDCs;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Execute implements CommandLineRunner {
	private ApplicationService applicationService;

	public Execute(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	@Override
	public void run(String... args) {

		String txId = UUID.randomUUID().toString();

		MDC.put(MDCs.REQUEST_ID, txId);
		MDC.put(MDCs.RESPONSE_CODE, "500");
		MDC.put(MDCs.RESPONSE_DESCRIPTION, "Internal Server Error");
		MDC.put(MDCs.RESPONSE_SEVERITY, "ERROR");
		MDC.put(MDCs.STATUS_CODE, "ERROR");
		MDC.put(MDCs.TARGET_ENTITY, "Onboarding");
		MDC.put(MDCs.TARGET_SERVICE_NAME, "Onboarding/api/v2");
		MDC.put(MDCs.CLIENT_IP_ADDRESS, "127.0.0.1");
		MDC.put(MDCs.SERVER_FQDN, "acumos-k8s.westus.cloud.aws.com");

		applicationService.logNormalThread();
		applicationService.logAsyncThread();
	}

}
