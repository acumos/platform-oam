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
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.acumos.elk.client.transport.ELkRepositoryMetaData;
import org.acumos.elk.client.transport.ElkGetRepositoriesResponse;
import org.acumos.elk.client.transport.ElkRepositoriesRequest;
import org.acumos.elk.client.transport.ErrorTransport;
import org.acumos.elk.client.utils.ElkClientConstants;
import org.elasticsearch.action.admin.cluster.repositories.delete.DeleteRepositoryRequest;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesRequest;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesResponse;
import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.RepositoryMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.repositories.fs.FsRepository;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implementation of operation related to elastic stack repository.
 *
 */
@Service
public class SnapshotRepositoryServiceImpl extends AbstractELKClientConnection implements ISnapshotRepositoryService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public ElkGetRepositoriesResponse getAllElkRepository() {

		logger.debug("Inside getAllElkRepository ");
		RestHighLevelClient client = restHighLevelClientConnection();
		GetRepositoriesRequest request1 = new GetRepositoriesRequest();
		request1.repositories();
		request1.local(true);

		try {
			GetRepositoriesResponse response = client.snapshot().getRepository(request1, RequestOptions.DEFAULT);
			List<RepositoryMetaData> repositoryMetaDataResponse = response.repositories();
			logger.debug("Number of repositoryMetaDataResponse size {}", repositoryMetaDataResponse.size());

			ElkGetRepositoriesResponse elkRepositoriesResponse = new ElkGetRepositoriesResponse();
			List<ELkRepositoryMetaData> repositories = new ArrayList<>();
			for (RepositoryMetaData snapshotStatusResponse : repositoryMetaDataResponse) {

				logger.debug("\nNAME: {}", snapshotStatusResponse.name());
				logger.debug("TYPE: {}", snapshotStatusResponse.type());
				logger.debug("SETTINGS: {}", snapshotStatusResponse.settings());

				ELkRepositoryMetaData repositoryMetaData = new ELkRepositoryMetaData();
				repositoryMetaData.setName(snapshotStatusResponse.name());
				repositoryMetaData.setType(snapshotStatusResponse.type());

				JSONObject settings = new JSONObject();
				for (String key : snapshotStatusResponse.settings().keySet()) {
					settings.put(key, snapshotStatusResponse.settings().get(key));
				}
				repositoryMetaData.setSettings(settings);
				repositories.add(repositoryMetaData);
			}
			elkRepositoriesResponse.setRepositories(repositories);
			client.close();
			return elkRepositoriesResponse;
		} catch (Exception ex) {
			logger.debug("Exception:", ex);
			throw new ErrorTransport("Unable to connect Elasticserach");
		}

	}

	public String createElkRepository(ElkRepositoriesRequest elkCreateRepositoriesRequest) {

		logger.debug("Inside createElkRepository ");
		RestHighLevelClient client = restHighLevelClientConnection();
		PutRepositoryRequest request = new PutRepositoryRequest();
		String locationKey = FsRepository.LOCATION_SETTING.getKey();
		String locationValue = ".";
		String compressKey = FsRepository.COMPRESS_SETTING.getKey();
		boolean compressValue = true;
		Settings settings = Settings.builder().put(locationKey, locationValue).put(compressKey, compressValue).build();
		request.settings(settings);
		request.name(elkCreateRepositoriesRequest.getRepositoryName());
		request.type(FsRepository.TYPE);
		request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
		request.masterNodeTimeout(ElkClientConstants.TIME_ONE_MINT_OUT);
		request.verify(true);
		AcknowledgedResponse acknowledgedResponse;
		boolean acknowledged = false;
		try {
			acknowledgedResponse = client.snapshot().createRepository(request, RequestOptions.DEFAULT);
			acknowledged = acknowledgedResponse.isAcknowledged();
			client.close();
		} catch (IOException ex) {
			logger.debug("Exception:", ex);
			throw new ErrorTransport("Unable to connect Elasticserach");
		}

		logger.debug("Repository is created(true for created)  {}", acknowledged);
		return String.valueOf(acknowledged);
	}

	@Override
	public String deleteElkRepository(ElkRepositoriesRequest elkDeleteRepositoriesRequest) {

		logger.debug("Inside deleteElkRepository");
		RestHighLevelClient client = restHighLevelClientConnection();
		DeleteRepositoryRequest deleteRepositoryRequest = new DeleteRepositoryRequest(
				elkDeleteRepositoriesRequest.getRepositoryName());
		deleteRepositoryRequest.masterNodeTimeout(elkDeleteRepositoriesRequest.getNodeTimeout());
		AcknowledgedResponse deleteAcknowledgedResponse;
		try {
			deleteAcknowledgedResponse = client.snapshot().deleteRepository(deleteRepositoryRequest,
					RequestOptions.DEFAULT);
			client.close();
		} catch (IOException ex) {
			logger.debug("Exception:", ex);
			throw new ErrorTransport("Unable to connect Elasticserach");
		}
		boolean deleteAcknowledged = deleteAcknowledgedResponse.isAcknowledged();
		logger.debug("Repository is delete(true for created)... {}", deleteAcknowledged);

		return String.valueOf(deleteAcknowledged);
	}

}
