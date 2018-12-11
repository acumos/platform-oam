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

import java.util.Map;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

/**
 * Copy MDC data from a Web thread context onto the asynchronous threads’
 * context.
 *
 */
public class LoggingTaskDecorator implements TaskDecorator {

	@Override
	public Runnable decorate(Runnable runnable) {
		// Right now: Web thread context !
		// (Grab the current thread MDC data)
		Map<String, String> contextMap = MDC.getCopyOfContextMap();
		return () -> {
			try {
				// Right now: @Async thread context !
				// (Restore the Web thread context's MDC data)
				MDC.setContextMap(contextMap);
				runnable.run();
			} finally {
				MDC.clear();
			}
		};
	}

}
