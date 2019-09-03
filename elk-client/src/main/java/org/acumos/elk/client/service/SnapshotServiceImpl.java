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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.acumos.elk.client.transport.CreateSnapshot;
import org.acumos.elk.client.transport.DeleteSnapshot;
import org.acumos.elk.client.transport.ELkRepositoryMetaData;
import org.acumos.elk.client.transport.ElasticStackIndiceResponse;
import org.acumos.elk.client.transport.ElasticStackIndices;
import org.acumos.elk.client.transport.ElasticsearchSnapshotsResponse;
import org.acumos.elk.client.transport.ElkCreateSnapshotRequest;
import org.acumos.elk.client.transport.ElkDeleteSnapshotRequest;
import org.acumos.elk.client.transport.ElkGetRepositoriesResponse;
import org.acumos.elk.client.transport.ElkGetSnapshotMetaData;
import org.acumos.elk.client.transport.ElkRepositoriesRequest;
import org.acumos.elk.client.transport.ElkRestoreSnapshotRequest;
import org.acumos.elk.client.transport.ElkSnapshotsResponse;
import org.acumos.elk.client.transport.ErrorTransport;
import org.acumos.elk.client.transport.RestoreSnapshot;
import org.acumos.elk.client.utils.ElkClientConstants;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotRequest;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotResponse;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotRequest;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsRequest;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsResponse;
import org.elasticsearch.action.admin.cluster.snapshots.restore.RestoreSnapshotRequest;
import org.elasticsearch.action.admin.cluster.snapshots.restore.RestoreSnapshotResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.snapshots.SnapshotInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.carrotsearch.hppc.cursors.ObjectCursor;

/**
 * Implementation of operation related to elastic stack snapshot.
 *
 */
@Service
public class SnapshotServiceImpl extends AbstractELKClientConnection implements ISnapshotService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	SnapshotRepositoryServiceImpl snapshotRepositoryServiceImpl;

	@Override
	public ElkSnapshotsResponse getAllElasticSearchSnapshot(ElkRepositoriesRequest elkRepositoriesRequest) {
		logger.debug("inside getAllElasticSearchSnapshot method ");
		Predicate<ElasticsearchSnapshotsResponse> chkSnapshots = obj -> (obj.getSnapshots() != null
				&& obj.getSnapshots().size() > 0);

		ElkSnapshotsResponse elkSnapshotsResponse = new ElkSnapshotsResponse();
		List<ElasticsearchSnapshotsResponse> elasticsearchSnapshotsResponseList = new ArrayList<>();
		ElkGetRepositoriesResponse elkGetRepositoriesResponse = snapshotRepositoryServiceImpl.getAllElkRepository();
		List<ELkRepositoryMetaData> repositories = elkGetRepositoriesResponse.getRepositories();
		for (ELkRepositoryMetaData eLkRepositoryMetaData : repositories) {
			ElasticsearchSnapshotsResponse elasticsearchSnapshotsResponse = getElasticsearchSnapshotDetails(
					elkRepositoriesRequest, eLkRepositoryMetaData.getName());
			if (chkSnapshots.test(elasticsearchSnapshotsResponse)) {
				elasticsearchSnapshotsResponseList.add(elasticsearchSnapshotsResponse);
			}
		}
		elkSnapshotsResponse.setElasticsearchSnapshots(elasticsearchSnapshotsResponseList);
		return elkSnapshotsResponse;
	}

	@Override
	public ElkSnapshotsResponse createElasticSearchSnapshot(ElkCreateSnapshotRequest createSnapshotUserRequest)
			throws Exception {
		logger.debug("Inside createElasticSearchSnapshot service method");
		ElkSnapshotsResponse elkSnapshotsResponse = new ElkSnapshotsResponse();
		if (createSnapshotUserRequest.getCreateSnapshots() != null
				&& !createSnapshotUserRequest.getCreateSnapshots().isEmpty()) {
			String snapshotName = null;
			ElasticsearchSnapshotsResponse snapshotResponse = new ElasticsearchSnapshotsResponse();
			List<CreateSnapshot> createSnapshots = createSnapshotUserRequest.getCreateSnapshots();
			for (CreateSnapshot createSnapshot : createSnapshots) {
				CreateSnapshotRequest createSnapshotRequest = new CreateSnapshotRequest();
				if (createSnapshotUserRequest.getNodeTimeout() != null
						&& Long.parseLong(createSnapshotUserRequest.getNodeTimeout()) > 2) {
					createSnapshotRequest.masterNodeTimeout(
							TimeValue.timeValueMinutes(Long.parseLong(createSnapshotUserRequest.getNodeTimeout())));
				} else {
					createSnapshotRequest.masterNodeTimeout(ElkClientConstants.TIME_TWO_MINT_OUT);
				}
				createSnapshotRequest.repository(createSnapshot.getRepositoryName());
				LocalDateTime dateTime = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
				String dateWithTime = dateTime.toString().replaceAll(":", "-")
						.substring(0, dateTime.toString().length() - 4).toLowerCase();
				snapshotName = "snapshot-" + dateWithTime;

				if (createSnapshot.getSnapshotName() != null
						&& !createSnapshot.getSnapshotName().equalsIgnoreCase("default")) {
					createSnapshotRequest.snapshot(createSnapshot.getSnapshotName());
				} else {
					createSnapshotRequest.snapshot(snapshotName);
				}

				RestHighLevelClient client = restHighLevelClientConnection();
				CreateSnapshotResponse createSnapshotResponse;
				try {
					logger.debug("createSnapshot getIndices size: {}", createSnapshot.getIndices().size());
					if (createSnapshot.getIndices() != null && createSnapshot.getIndices().size() > 0) {
						List<String> indices = createSnapshot.getIndices();
						createSnapshotRequest.indices(indices);
					} else {
						ElasticStackIndices elkIndicesResponse = getAllElasticSearchIndices();
						List<String> indices = elkIndicesResponse.getIndices();
						createSnapshotRequest.indices(indices);
					}
					createSnapshotResponse = client.snapshot().create(createSnapshotRequest, RequestOptions.DEFAULT);
				} catch (ElasticsearchException ex) {
					logger.debug("ElasticsearchException:", ex);
					throw ex;
				} catch (Exception ex) {
					logger.debug("Exception:", ex);
					throw ex;
				} finally {
					if (client != null) {
						client.close();
					}
				}
				if (createSnapshotResponse != null && createSnapshotResponse.getSnapshotInfo() != null) {
					SnapshotInfo snapshotInfo = createSnapshotResponse.getSnapshotInfo();
					RestStatus status = createSnapshotResponse.status();
					logger.debug(
							"\\nSnapshotInfo \n snapshotId: {} \n status: {} \n state: {} \n startTime: {} \n endTime: {} \n successfulShards: {} \n RestStatus: {}",
							snapshotInfo.snapshotId(), snapshotInfo.status(), snapshotInfo.state(),
							snapshotInfo.startTime(), convertTime(snapshotInfo.endTime()),
							convertTime(snapshotInfo.successfulShards()), status.getStatus());
					List<ElasticsearchSnapshotsResponse> elasticsearchSnapshotsResponseList = new ArrayList<>();
					List<ElkGetSnapshotMetaData> snapshotMetaDatas = new ArrayList<>();
					ElkGetSnapshotMetaData elkGetSnapshotMetaData = new ElkGetSnapshotMetaData();
					elkGetSnapshotMetaData.setSnapShotId(snapshotInfo.snapshotId().toString());
					elkGetSnapshotMetaData.setStatus(snapshotInfo.status().toString());
					elkGetSnapshotMetaData.setState(snapshotInfo.state().toString());
					elkGetSnapshotMetaData.setStartTime(String.valueOf(snapshotInfo.startTime()));
					elkGetSnapshotMetaData.setEndTime(String.valueOf(snapshotInfo.endTime()));
					snapshotMetaDatas.add(elkGetSnapshotMetaData);
					snapshotResponse.setSnapshots(snapshotMetaDatas);
					snapshotResponse.setRepositoryName(createSnapshot.getRepositoryName());
					elasticsearchSnapshotsResponseList.add(snapshotResponse);
					elkSnapshotsResponse.setElasticsearchSnapshots(elasticsearchSnapshotsResponseList);
				} else {
					List<ElasticsearchSnapshotsResponse> elasticsearchSnapshotsResponseList = new ArrayList<>();
					List<ElkGetSnapshotMetaData> snapshotMetaDatas = new ArrayList<>();
					ElkGetSnapshotMetaData elkGetSnapshotMetaData = new ElkGetSnapshotMetaData();
					elkGetSnapshotMetaData.setSnapShotId(snapshotName);
					elkGetSnapshotMetaData
							.setStatus("Snapshot creation is in progress. Will take some time due size of data.");
					snapshotMetaDatas.add(elkGetSnapshotMetaData);
					snapshotResponse.setSnapshots(snapshotMetaDatas);
					snapshotResponse.setRepositoryName(createSnapshot.getRepositoryName());
					elasticsearchSnapshotsResponseList.add(snapshotResponse);
					elkSnapshotsResponse.setElasticsearchSnapshots(elasticsearchSnapshotsResponseList);
				}
			}
		}
		logger.debug("Snapshot is created(true for created)");
		return elkSnapshotsResponse;
	}

	@Override
	public ElkSnapshotsResponse deleteElasticSearchSnapshot(ElkDeleteSnapshotRequest elkDeleteSnapshotRequest) {
		logger.debug("Inside deleteElasticSearchSnapshot service method");
		List<DeleteSnapshot> deleteSnapshots = elkDeleteSnapshotRequest.getDeleteSnapshots();
		RestHighLevelClient client = restHighLevelClientConnection();
		if (deleteSnapshots != null && !deleteSnapshots.isEmpty()) {
			ElkSnapshotsResponse elkSnapshotsResponse = new ElkSnapshotsResponse();
			List<ElasticsearchSnapshotsResponse> elasticsearchSnapshotsResponseList = new ArrayList<>();
			for (DeleteSnapshot deleteSnapshot : deleteSnapshots) {

				if (deleteSnapshot.getSnapShotId() != null) {
					DeleteSnapshotRequest deleteSnapshotRequest = new DeleteSnapshotRequest(
							deleteSnapshot.getRepositoryName());
					logger.debug("snapshotId: {}", deleteSnapshot.getSnapShotId());
					deleteSnapshotRequest.snapshot(deleteSnapshot.getSnapShotId());
					deleteSnapshotRequest.masterNodeTimeout(ElkClientConstants.TIME_ONE_MINT_OUT);
					AcknowledgedResponse deleteSnapshotAcknowledgedResponse;
					try {
						deleteSnapshotAcknowledgedResponse = client.snapshot().delete(deleteSnapshotRequest,
								RequestOptions.DEFAULT);
						client.close();
					} catch (IOException ex) {
						logger.debug("Exception:", ex);
						throw new ErrorTransport("Unable to connect Elasticserach");
					}

					ElasticsearchSnapshotsResponse elasticsearchSnapshotsResponse = new ElasticsearchSnapshotsResponse();
					List<ElkGetSnapshotMetaData> snapshotMetaDatas = new ArrayList<>();
					ElkGetSnapshotMetaData elkGetSnapshotMetaData = new ElkGetSnapshotMetaData();
					elkGetSnapshotMetaData.setSnapShotId(deleteSnapshot.getSnapShotId());
					elkGetSnapshotMetaData
							.setStatus(String.valueOf(deleteSnapshotAcknowledgedResponse.isAcknowledged()));
					snapshotMetaDatas.add(elkGetSnapshotMetaData);
					elasticsearchSnapshotsResponse.setSnapshots(snapshotMetaDatas);
					elasticsearchSnapshotsResponse.setRepositoryName(deleteSnapshot.getRepositoryName());
					elasticsearchSnapshotsResponseList.add(elasticsearchSnapshotsResponse);
				}
			}
			elkSnapshotsResponse.setElasticsearchSnapshots(elasticsearchSnapshotsResponseList);
			return elkSnapshotsResponse;
		}

		return null;
	}

	@Override
	public ElasticStackIndiceResponse restoreElasticSearchSnapshot(ElkRestoreSnapshotRequest elkRestoreSnapshotRequest)
			throws IOException {
		logger.debug("Inside restoreElasticSearchSnapshot service ");
		ElasticStackIndiceResponse elasticStackIndiceResponse = new ElasticStackIndiceResponse();
		RestHighLevelClient client = restHighLevelClientConnection();
		for (RestoreSnapshot restoreSnapshot : elkRestoreSnapshotRequest.getRestoreSnapshots()) {
			try {
				RestoreSnapshotRequest restoreSnapshotRequest = new RestoreSnapshotRequest(
						elkRestoreSnapshotRequest.getRepositoryName(), restoreSnapshot.getSnapshotName());
				RestoreSnapshotResponse restoreSnapshotResponse = client.snapshot().restore(restoreSnapshotRequest,
						RequestOptions.DEFAULT);
				elasticStackIndiceResponse.setMessage(
						"ElasticStack Snapshot restore is in progress, depending size it will take some time");
				elasticStackIndiceResponse.setStatus(restoreSnapshotResponse.getRestoreInfo().status().toString());
				logger.debug("RestoreSnapshotResponse : {}, Status: {}", elasticStackIndiceResponse.getMessage(),
						restoreSnapshotResponse.getRestoreInfo().status().toString());
			} catch (IOException e) {
				logger.error("IOException: ", e);
				throw new ErrorTransport("Unable to connect Elasticserach");
			}
		}
		return elasticStackIndiceResponse;
	}

	@Override
	public ElasticStackIndices getAllElasticSearchIndices() throws IOException {

		logger.debug("Inside getAllElasticSearchIndices");
		RestHighLevelClient client = restHighLevelClientConnection();

		ElasticStackIndices elkIndicesResponse = new ElasticStackIndices();
		List<String> indices = new ArrayList<>();
		try {
			IndicesClient indicesAdminClient = client.indices();
			GetSettingsRequest request = new GetSettingsRequest();
			request.includeDefaults(true);
			request.indicesOptions(IndicesOptions.lenientExpandOpen());
			GetSettingsResponse getSettingsResponse = indicesAdminClient.getSettings(request, RequestOptions.DEFAULT);
			ImmutableOpenMap<String, Settings> indexMap = getSettingsResponse.getIndexToDefaultSettings();
			for (ObjectCursor<String> key : indexMap.keys()) {
				logger.debug("key.value: {}", key.value);
				if (key.value != ".kibana") {
					indices.add(key.value);
				}
			}
			elkIndicesResponse.setIndices(indices);

		} catch (IOException e1) {
			logger.debug("IOException: {}", e1);
			throw new ErrorTransport("Unable to connect Elasticserach");
		}

		return elkIndicesResponse;
	}

	public ElasticStackIndiceResponse deleteElasticSearchIndices(ElasticStackIndices elasticStackIndices)
			throws IOException {
		logger.debug("Inside deleteElasticSearchIndices");
		String response = null;
		ElasticStackIndiceResponse elasticStackIndiceResponse = new ElasticStackIndiceResponse();
		RestHighLevelClient client = restHighLevelClientConnection();
		try {
			IndicesClient indicesAdminClient = client.indices();
			List<String> indices = elasticStackIndices.getIndices();
			for (String indiceName : indices) {
				DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest();
				deleteIndexRequest.indices(indiceName);
				AcknowledgedResponse acknowledgedResponse = indicesAdminClient.delete(deleteIndexRequest,
						RequestOptions.DEFAULT);
				if (acknowledgedResponse.isAcknowledged()) {
					elasticStackIndiceResponse
							.setMessage("Indices delete is in progress, depending size it will take some time");
					elasticStackIndiceResponse.setStatus(ElkClientConstants.SUCCESS);
				} else {
					elasticStackIndiceResponse.setMessage("Indices is not deleted yet");
					elasticStackIndiceResponse.setStatus(ElkClientConstants.FAIL);
				}
				logger.debug("{} ,Indice Name: {}", elasticStackIndiceResponse.getMessage(), indiceName);
			}
		} catch (IOException e) {
			logger.debug("IOException: {}", e);
			throw new ErrorTransport("Unable to connect Elasticserach");
		}
		return elasticStackIndiceResponse;
	}

	protected ElasticsearchSnapshotsResponse getElasticsearchSnapshotDetails(
			ElkRepositoriesRequest elkRepositoriesRequest, String repositoryName) {

		RestHighLevelClient client = restHighLevelClientConnection();
		GetSnapshotsRequest getSnapshotsRequest = new GetSnapshotsRequest();
		if (elkRepositoriesRequest != null && elkRepositoriesRequest.getRepositoryName() != null
				&& !elkRepositoriesRequest.getRepositoryName().isEmpty()) {
			getSnapshotsRequest.repository(elkRepositoriesRequest.getRepositoryName());
		} else {
			getSnapshotsRequest.repository(repositoryName);
		}

		String[] snapshots = { "_all" };
		getSnapshotsRequest.snapshots(snapshots);
		getSnapshotsRequest.masterNodeTimeout(ElkClientConstants.TIME_ONE_MINT_OUT);
		getSnapshotsRequest.verbose(true);
		getSnapshotsRequest.ignoreUnavailable(false);
		GetSnapshotsResponse response;
		try {
			response = client.snapshot().get(getSnapshotsRequest, RequestOptions.DEFAULT);
			client.close();
		} catch (IOException ex) {
			logger.debug("Exception:", ex);
			throw new ErrorTransport("Unable to connect Elasticserach");
		}
		logger.debug("GetSnapshotsResponse.. {}", response);
		ElasticsearchSnapshotsResponse snapshotResponse = new ElasticsearchSnapshotsResponse();

		List<ElkGetSnapshotMetaData> snapshotMetaDatas = new ArrayList<>();
		for (SnapshotInfo snapshotInfo : response.getSnapshots()) {
			logger.debug(
					"\nsnapshotId: {} \n status: {} \n state: {} \n startTime: {} \n endTime: {} \n successfulShards: {}",
					snapshotInfo.snapshotId(), snapshotInfo.status(), snapshotInfo.state(), snapshotInfo.startTime(),
					convertTime(snapshotInfo.endTime()), convertTime(snapshotInfo.successfulShards()));
			ElkGetSnapshotMetaData elkGetSnapshotMetaData = new ElkGetSnapshotMetaData();
			String[] snapShotName = snapshotInfo.snapshotId().toString().split("/");
			elkGetSnapshotMetaData.setSnapShotId(snapShotName[0]);
			elkGetSnapshotMetaData.setStatus(snapshotInfo.status().toString());
			elkGetSnapshotMetaData.setState(snapshotInfo.state().toString());
			elkGetSnapshotMetaData.setStartTime(convertTime(snapshotInfo.startTime()));
			elkGetSnapshotMetaData.setEndTime(convertTime(snapshotInfo.endTime()));
			List<String> indices = snapshotInfo.indices();
			for (String indice : indices) {
				logger.debug("indices: {}", indice);
			}
			elkGetSnapshotMetaData.setIndices(indices);
			snapshotMetaDatas.add(elkGetSnapshotMetaData);
		}
		snapshotResponse.setRepositoryName(repositoryName);
		snapshotResponse.setSnapshots(snapshotMetaDatas);
		return snapshotResponse;
	}

	public String convertTime(long time) {
		Date date = new Date(time);
		Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

}
