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

import org.acumos.elk.client.transport.ElkGetRepositoriesResponse;
import org.acumos.elk.client.transport.ElkRepositoriesRequest;

/** 
 * This interface defines method for elastic stack repository operation. 
 *
 * */
public interface ISnapshotRepositoryService {

	/**
	 * This method will return all the repository present in elastic stack.
	 * 
	 * @return ElkGetRepositoriesResponse
	 */
	public ElkGetRepositoriesResponse getAllElkRepository();

	/**
	 * This method is used to create repository.
	 * 
	 * @param elkCreateRepositoriesRequest
	 *            takes as request parameter
	 * @return created status.
	 */
	public String createElkRepository(ElkRepositoriesRequest elkCreateRepositoriesRequest);

	/**
	 * This method is used to delete repository.
	 * 
	 * @param elkDeleteRepositoriesRequest
	 *            takes repository details.
	 * @return delete status.
	 */
	public String deleteElkRepository(ElkRepositoriesRequest elkDeleteRepositoriesRequest);

}
