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

public class ElkDeleteSnapshotRequest {

	@ApiModelProperty(value = "DeleteSnapshots is required")
	private List<DeleteSnapshot> deleteSnapshots;
	@ApiModelProperty(required = true, value = "Value numeric values, ideal value is between 1 to 3", example = "1")
	private String nodeTimeout;

	public List<DeleteSnapshot> getDeleteSnapshots() {
		return deleteSnapshots;
	}

	public void setDeleteSnapshots(List<DeleteSnapshot> deleteSnapshots) {
		this.deleteSnapshots = deleteSnapshots;
	}

	public String getNodeTimeout() {
		return nodeTimeout;
	}

	public void setNodeTimeout(String nodeTimeout) {
		this.nodeTimeout = nodeTimeout;
	}

}
