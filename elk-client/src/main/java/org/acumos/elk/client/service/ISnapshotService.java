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

import java.io.IOException;

import org.acumos.elk.client.transport.ElkCreateSnapshotRequest;
import org.acumos.elk.client.transport.ElkDeleteSnapshotRequest;
import org.acumos.elk.client.transport.ElkRepositoriesRequest;
import org.acumos.elk.client.transport.ElkRestoreSnapshotRequest;
import org.acumos.elk.client.transport.ElkSnapshotsResponse;

/** This interface defines method for elastic stack snapshot operation. */
public interface ISnapshotService {

	/**
	 * This method will return all the repository present in elastic stack.
	 * 
	 * @param elkRepositoriesRequest
	 *            takes repository details.
	 * @return ElkSnapshotsResponse contains all the snapshot details.
	 */
	public ElkSnapshotsResponse getAllElasticSearchSnapshot(ElkRepositoriesRequest elkRepositoriesRequest);

	/**
	 * This method is used to create Snapshot.
	 * 
	 * @param createDeleteSnapshotRequest
	 *            takes as request parameter
	 * @return ElkSnapshotsResponse contains snapshot creation status
	 * @throws Exception
	 */
	public ElkSnapshotsResponse createElasticSearchSnapshot(ElkCreateSnapshotRequest createDeleteSnapshotRequest)
			throws Exception;

	/**
	 * This method is used to delete Snapshot.
	 * 
	 * @param deleteSnapshotRequest
	 *            takes as request parameter
	 * @return ElkSnapshotsResponse contains snapshot delete status
	 */
	public ElkSnapshotsResponse deleteElasticSearchSnapshot(ElkDeleteSnapshotRequest deleteSnapshotRequest);

	/**
	 * This method is used to restore Snapshot.
	 * 
	 * @param elkRestoreSnapshotRequest
	 *            takes as request parameter
	 * @return contains snapshot restore status
	 * @throws IOException 
	 */
	public ElkSnapshotsResponse restoreElasticSearchSnapshot(ElkRestoreSnapshotRequest elkRestoreSnapshotRequest) throws IOException;

}
