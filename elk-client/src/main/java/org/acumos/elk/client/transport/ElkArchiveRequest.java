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
package org.acumos.elk.client.transport;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ElkArchiveRequest {

	@ApiModelProperty(required = true, value = "String value archive/restore/delete", example = "archive/restore/delete")
	private String action;

	@ApiModelProperty(required = true)
	private List<String> repositoryName;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<String> getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(List<String> repositoryName) {
		this.repositoryName = repositoryName;
	}

}