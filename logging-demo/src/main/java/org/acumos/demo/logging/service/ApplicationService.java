/*
  ===============LICENSE_START=======================================================
  Acumos
  ===================================================================================
  Copyright (C) 2017 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
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
package org.acumos.demo.logging.service;

import org.acumos.demo.logging.util.ACUMOSLogConstants.InvocationMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
	private static final Logger log = LoggerFactory.getLogger(ApplicationService.class);
	

    public void logNormalThread() {
    
    	log.info(InvocationMode.SYNCHRONOUS.getMarker(),"platform-oam");
    
    }

    @Async
    public void logAsyncThread() {
    
    	log.info(InvocationMode.ASYNCHRONOUS.getMarker(),"platform-oam");
        
    }

}
