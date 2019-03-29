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

import io.swagger.annotations.ApiModelProperty;

public class DeleteSnapshot {

	@ApiModelProperty(required = true, value = "ElasticStack repository name", example = "logstash")
	private String repositoryName;
	@ApiModelProperty(value = "snapshot-2019-03-28t08-53-41", example = "snapshot-2019-03-28t08-53-41")
	private String snapShotId;
	@ApiModelProperty(required = true, value = "Value ture/false is allowed", example = "true")
	private String delete;

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getSnapShotId() {
		return snapShotId;
	}

	public void setSnapShotId(String snapShotId) {
		this.snapShotId = snapShotId;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((delete == null) ? 0 : delete.hashCode());
		result = prime * result + ((repositoryName == null) ? 0 : repositoryName.hashCode());
		result = prime * result + ((snapShotId == null) ? 0 : snapShotId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeleteSnapshot other = (DeleteSnapshot) obj;
		if (delete == null) {
			if (other.delete != null)
				return false;
		} else if (!delete.equals(other.delete))
			return false;
		if (repositoryName == null) {
			if (other.repositoryName != null)
				return false;
		} else if (!repositoryName.equals(other.repositoryName))
			return false;
		if (snapShotId == null) {
			if (other.snapShotId != null)
				return false;
		} else if (!snapShotId.equals(other.snapShotId))
			return false;
		return true;
	}

}
