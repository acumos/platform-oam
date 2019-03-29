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
package org.acumos.elk.client;

import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.acumos.elk.client.service.ISnapshotRepositoryService;
import org.acumos.elk.client.service.ISnapshotService;
import org.acumos.elk.client.transport.CreateSnapshot;
import org.acumos.elk.client.transport.ELkRepositoryMetaData;
import org.acumos.elk.client.transport.ElasticsearchSnapshotsResponse;
import org.acumos.elk.client.transport.ElkCreateSnapshotRequest;
import org.acumos.elk.client.transport.ElkGetRepositoriesResponse;
import org.acumos.elk.client.transport.ElkGetSnapshotMetaData;
import org.acumos.elk.client.transport.ElkSnapshotsResponse;
import org.acumos.elk.client.utils.ElkClientConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Managing Schedule cron job for snapshot creation.
 *
 */
@Component
public class ElkLogSnapshotTaskSchedular {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	ISnapshotService snapshotService;

	@Autowired
	ISnapshotRepositoryService snapshotGetRepositoryService;

	@Scheduled(cron = "${elk.cronschedule.createsnapshot.time}")
	public void createLogSnapshot() throws Exception {
		logger.debug("Inside createLogSnapshot");
		logger.info("The time is now {}", dateFormat.format(new Date()));
		try {

			ElkGetRepositoriesResponse elkGetRepositoriesResponse = snapshotGetRepositoryService.getAllElkRepository();
			List<ELkRepositoryMetaData> repositories = elkGetRepositoriesResponse.getRepositories();

			for (ELkRepositoryMetaData eLkRepositoryMetaData : repositories) {
				ElkCreateSnapshotRequest createDeleteSnapshotRequest = new ElkCreateSnapshotRequest();
				List<CreateSnapshot> createSnapshots = new ArrayList<>();
				CreateSnapshot createSnapshot = new CreateSnapshot();
				createSnapshot.setCreate(ElkClientConstants.TRUE);
				createSnapshot.setRepositoryName(eLkRepositoryMetaData.getName());
				createSnapshots.add(createSnapshot);
				createDeleteSnapshotRequest.setCreateSnapshots(createSnapshots);
				createDeleteSnapshotRequest.setNodeTimeout("1");
				ElkSnapshotsResponse response = snapshotService
						.createElasticSearchSnapshot(createDeleteSnapshotRequest);
				List<ElasticsearchSnapshotsResponse> elasticsearchSnapshots = response.getElasticsearchSnapshots();

				for (ElasticsearchSnapshotsResponse elasticsearchSnapshotsResponse : elasticsearchSnapshots) {
					logger.debug("RepositoryName: {}", elasticsearchSnapshotsResponse.getRepositoryName());
					List<ElkGetSnapshotMetaData> snapshots = elasticsearchSnapshotsResponse.getSnapshots();
					for (ElkGetSnapshotMetaData elkGetSnapshotMetaData : snapshots) {
						logger.debug(
								"\n########################################## " + "\n SnapShotId:{}  \nStatus:{}"
										+ "\n########################################## ",
								elkGetSnapshotMetaData.getSnapShotId(), elkGetSnapshotMetaData.getStatus());
					}
				}

			}

		} catch (Exception e) {
			logger.debug("Exception", e);
			throw e;
		}
		logger.info("The time is now {}", dateFormat.format(new Date()));
	}

}
