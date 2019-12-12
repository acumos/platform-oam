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
package org.acumos.elk.client.controller;

import java.lang.invoke.MethodHandles;
import java.util.Date;

import org.acumos.elk.client.service.ISnapshotRepositoryService;
import org.acumos.elk.client.service.ISnapshotService;
import org.acumos.elk.client.transport.ElasticStackIndiceResponse;
import org.acumos.elk.client.transport.ElasticStackIndices;
import org.acumos.elk.client.transport.ElkArchiveRequest;
import org.acumos.elk.client.transport.ElkArchiveResponse;
import org.acumos.elk.client.transport.ElkCreateSnapshotRequest;
import org.acumos.elk.client.transport.ElkDeleteSnapshotRequest;
import org.acumos.elk.client.transport.ElkGetRepositoriesResponse;
import org.acumos.elk.client.transport.ElkRepositoriesRequest;
import org.acumos.elk.client.transport.ElkRestoreSnapshotRequest;
import org.acumos.elk.client.transport.ElkSnapshotsResponse;
import org.acumos.elk.client.transport.ErrorDetails;
import org.acumos.elk.client.transport.ErrorTransport;
import org.acumos.elk.client.utils.ElkClientConstants;
import org.acumos.elk.logging.LogConfig;
import org.elasticsearch.ElasticsearchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.acumos.elk.exception.ELKException;

import io.swagger.annotations.ApiOperation;

/**
 * Operation related to Acumos elastic stack platform. Create, Delete, List
 * repository and snapshot are operation provide.
 */
@RestController
public class ElasticSearchServiceController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	ISnapshotService snapshotService;

	@Autowired
	ISnapshotRepositoryService snapshotGetRepositoryService;

	@ApiOperation(value = "Get all the indices of Elasticstack.")
	@RequestMapping(value = ElkClientConstants.GET_ALL_INDICES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ElasticStackIndices> getElkIndices() throws Exception {
		LogConfig.setEnteringMDCs("elk-client", ElkClientConstants.GET_ALL_INDICES);
		logger.debug("Inside getElkIndices Service");
		ElasticStackIndices response = snapshotService.getAllElasticSearchIndices();
		logger.debug("method call ended.");
		LogConfig.clearMDCDetails();
		return new ResponseEntity<ElasticStackIndices>(response, null, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete elasticstack Indices.")
	@RequestMapping(value = ElkClientConstants.DELETE_INDICES, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ElasticStackIndiceResponse> deleteElkSnapshot(
			@RequestBody ElasticStackIndices elasticStackIndices) throws Exception {
		LogConfig.setEnteringMDCs("elk-client", ElkClientConstants.DELETE_INDICES);
		logger.debug("Inside delete elasticstack indices");
		ElasticStackIndiceResponse elasticStackIndiceResponse = snapshotService
				.deleteElasticSearchIndices(elasticStackIndices);
		logger.debug("method call ended.");
		LogConfig.clearMDCDetails();
		return new ResponseEntity<ElasticStackIndiceResponse>(elasticStackIndiceResponse, null, HttpStatus.OK);
	}

	@ApiOperation(value = "Get all the elasticsearch repositories details of Elasticstack.")
	@RequestMapping(value = ElkClientConstants.GET_ALL_REPOSITORIES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ElkGetRepositoriesResponse> getElkRepository() throws Exception {
		LogConfig.setEnteringMDCs("elk-client", ElkClientConstants.GET_ALL_REPOSITORIES);
		logger.debug("Inside getElkRepository Service");
		ElkGetRepositoriesResponse response = snapshotGetRepositoryService.getAllElkRepository();
		logger.debug("method call ended.");
		LogConfig.clearMDCDetails();
		return new ResponseEntity<ElkGetRepositoriesResponse>(response, null, HttpStatus.OK);
	}

	@ApiOperation(value = "Create Elasticstack repository.")
	@RequestMapping(value = ElkClientConstants.SNAPSHOT_CREATE_REPOSITORY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createElkRepository(@RequestBody ElkRepositoriesRequest elkCreateRepositoriesRequest)
			throws Exception {
		LogConfig.setEnteringMDCs("elk-client", ElkClientConstants.SNAPSHOT_CREATE_REPOSITORY);
		String repositoryStatus = null;
		logger.debug("Inside create elasticstack repository");

		repositoryStatus = snapshotGetRepositoryService.createElkRepository(elkCreateRepositoriesRequest);

		logger.debug("method call ended.");
		LogConfig.clearMDCDetails();
		return new ResponseEntity<String>(repositoryStatus, null, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete Elasticstack repository.")
	@RequestMapping(value = ElkClientConstants.SNAPSHOT_DELETE_REPOSITORY_REQUEST, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteElkRepository(@RequestBody ElkRepositoriesRequest elkDeleteRepositoriesRequest)
			throws Exception {
		LogConfig.setEnteringMDCs("elk-client", ElkClientConstants.SNAPSHOT_DELETE_REPOSITORY_REQUEST);
		logger.debug("Inside delete elasticstack repository");
		String repositoryStatus = snapshotGetRepositoryService.deleteElkRepository(elkDeleteRepositoriesRequest);
		logger.debug("method call ended.");
		LogConfig.clearMDCDetails();
		return new ResponseEntity<String>(repositoryStatus, null, HttpStatus.OK);
	}

	@ApiOperation(value = "Get all the elasticsearch snapshot.", response = ElkSnapshotsResponse.class)
	@RequestMapping(value = ElkClientConstants.GET_ALL_SNAPSHOTS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ElkSnapshotsResponse getElkSnapshot() throws Exception {
		LogConfig.setEnteringMDCs("elk-client", ElkClientConstants.GET_ALL_SNAPSHOTS);
		logger.debug("Inside get elasticstack snapshot");
		ElkRepositoriesRequest elkRepositoriesRequest = new ElkRepositoriesRequest();
		ElkSnapshotsResponse response = snapshotService.getAllElasticSearchSnapshot(elkRepositoriesRequest);
		logger.debug("method call ended.");
		LogConfig.clearMDCDetails();
		return response;
	}

	@ApiOperation(value = "Create elasticstack snapshot.")
	@RequestMapping(value = ElkClientConstants.CREATE_SNAPSHOT_REQUEST, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ElkSnapshotsResponse> createElkSnapshot(
			@RequestBody ElkCreateSnapshotRequest createSnapshotRequest) throws Exception {
		LogConfig.setEnteringMDCs("elk-client", ElkClientConstants.CREATE_SNAPSHOT_REQUEST);
		logger.debug("Inside create elasticstack repository");
		ElkSnapshotsResponse response = snapshotService.createElasticSearchSnapshot(createSnapshotRequest);
		logger.debug("method call ended.");
		LogConfig.clearMDCDetails();
		return new ResponseEntity<ElkSnapshotsResponse>(response, null, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete elasticstack snapshot.")
	@RequestMapping(value = ElkClientConstants.DELETE_SNAPSHOT_REQUEST, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ElkSnapshotsResponse> deleteElkSnapshot(
			@RequestBody ElkDeleteSnapshotRequest elkDeleteSnapshotRequest) throws Exception {
		LogConfig.setEnteringMDCs("elk-client", ElkClientConstants.DELETE_SNAPSHOT_REQUEST);
		logger.debug("Inside create elasticstack repository");
		ElkSnapshotsResponse response = snapshotService.deleteElasticSearchSnapshot(elkDeleteSnapshotRequest);
		logger.debug("method call ended.");
		LogConfig.clearMDCDetails();
		return new ResponseEntity<ElkSnapshotsResponse>(response, null, HttpStatus.OK);
	}

	@ApiOperation(value = "Restore elasticstack snapshot.")
	@RequestMapping(value = ElkClientConstants.RESTORE_SNAPSHOT_REQUEST, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ElasticStackIndiceResponse> restoreElkSnapshot(
			@RequestBody ElkRestoreSnapshotRequest elkRestoreSnapshotRequest) throws Exception {
		LogConfig.setEnteringMDCs("elk-client", ElkClientConstants.RESTORE_SNAPSHOT_REQUEST);
		logger.debug("Inside restore elasticstack snapshot");
		ElasticStackIndiceResponse elasticStackIndiceResponse = snapshotService
				.restoreElasticSearchSnapshot(elkRestoreSnapshotRequest);
		logger.debug("restore method call ended.");
		LogConfig.clearMDCDetails();
		return new ResponseEntity<ElasticStackIndiceResponse>(elasticStackIndiceResponse, null, HttpStatus.OK);
	}

	@ApiOperation(value = "Get all the archive snapshot.")
	@RequestMapping(value = ElkClientConstants.GET_ALL_ARCHIVE_INFO, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ElkArchiveResponse> getAllArchiveInfoElkSnapshot() throws Exception {
		LogConfig.setEnteringMDCs("elk-client", ElkClientConstants.GET_ALL_ARCHIVE_INFO);
		logger.debug("Inside getAllArchiveInfoElkSnapshot");
		ElkArchiveResponse archiveResponse = snapshotGetRepositoryService.getArchiveElkRepository();
		logger.debug("method call ended.");
		LogConfig.clearMDCDetails();
		return new ResponseEntity<ElkArchiveResponse>(archiveResponse, null, HttpStatus.OK);
	}

	@ApiOperation(value = "Archive and Restore elasticstack snapshot.")
	@RequestMapping(value = ElkClientConstants.ARCHIVE_REQUEST, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ElkArchiveResponse> archiveElkSnapshot(@RequestBody ElkArchiveRequest archiveRequest)
			throws Exception {
		LogConfig.setEnteringMDCs("elk-client", ElkClientConstants.ARCHIVE_REQUEST);
		logger.debug("Inside archiveElkSnapshot");
		ElkArchiveResponse archiveResponse = snapshotGetRepositoryService.archiveElkRepository(archiveRequest);
		logger.debug("method call ended.");
		LogConfig.clearMDCDetails();
		return new ResponseEntity<ElkArchiveResponse>(archiveResponse, null, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ErrorTransport.class)
	public ResponseEntity<ErrorDetails> handleTransportError(ErrorTransport ex, WebRequest request) {
		logger.error("IOException handler executed");
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		LogConfig.clearMDCDetails();
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleException(Exception ex, WebRequest request) {
		logger.error("IOException handler executed");
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		LogConfig.clearMDCDetails();
		return new ResponseEntity<>(errorDetails, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ElasticsearchException.class)
	public ResponseEntity<ErrorDetails> handleElasticsearchException(ElasticsearchException ex, WebRequest request) {
		logger.error("IOException handler executed");
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		LogConfig.clearMDCDetails();
		return new ResponseEntity<>(errorDetails, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
