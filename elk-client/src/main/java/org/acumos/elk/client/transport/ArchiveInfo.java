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

public class ArchiveInfo {

	@ApiModelProperty(required = true, value = "snapshot creation date")
	private String date;

	@ApiModelProperty(required = true, value = "snapshot name.")
	private String repositoryName;

	private List<ElkGetSnapshotMetaData> snapshots;

	public ArchiveInfo(String date, String repositoryName) {
		super();
		this.date = date;
		this.repositoryName = repositoryName;
	}

	public ArchiveInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getDate() {
		return date;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public List<ElkGetSnapshotMetaData> getSnapshots() {
		return snapshots;
	}

	public void setSnapshots(List<ElkGetSnapshotMetaData> snapshots) {
		this.snapshots = snapshots;
	}

	@Override
	public String toString() {
		return "ArchiveInfo [date=" + date + ", repositoryName=" + repositoryName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((repositoryName == null) ? 0 : repositoryName.hashCode());
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
		ArchiveInfo other = (ArchiveInfo) obj;
		if (repositoryName == null) {
			if (other.repositoryName != null)
				return false;
		} else if (!repositoryName.equals(other.repositoryName))
			return false;
		return true;
	}

}
