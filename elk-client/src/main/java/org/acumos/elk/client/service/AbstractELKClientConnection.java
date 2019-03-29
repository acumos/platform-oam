/*-
 * ===============LICENSE_START=======================================================
 * Acumos
 * ===================================================================================
 * Copyright (C) 2019 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
 * ===================================================================================
 * This Acumos software file is distributed by AT&T and Tech Mahindra
 * under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * This file is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===============LICENSE_END=========================================================
 */
package org.acumos.elk.client.service;

import java.lang.invoke.MethodHandles;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.acumos.elk.client.transport.ErrorTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public abstract class AbstractELKClientConnection {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	@Autowired
	private Environment env;

	public void setEnvironment(Environment env1) {
		env = env1;
	}

	public RestHighLevelClient restHighLevelClientConnection() {

		String elk_host_ip =  getHostIP(env.getProperty("elk.host.url"));
		int elk_elasticssearch_port = Integer.valueOf(env.getProperty("elk.elasticssearch.port"));

		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(elk_host_ip, elk_elasticssearch_port, "http")));
		return client;
	}

	private String getHostIP(String url) {
		try {
            InetAddress host = InetAddress.getByName(url);
            System.out.println(host.getHostAddress());
            logger.debug("Elasticssearch Host IP:", host.getHostAddress());
            return host.getHostAddress();
        } catch (UnknownHostException ex) {
            logger.error("UnknownHostException:", ex);
            throw new ErrorTransport("Unable to connect Elasticserach");
        }
	}
	
	

}
