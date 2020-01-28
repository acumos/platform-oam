/*-
 * ===============LICENSE_START=======================================================
 * Acumos
 * ===================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
 * Modifications Copyright (C) 2019 Nordix Foundation.
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

package com.logging.rest.example.springbootswagger2.controller;

/**
 *
 * Constants class to list all the REST API endpoints
 */
public class APINames {

	public static final String CHARSET = "application/json;charset=utf-8";

	public static final String GET_CATALOG_TEST = "/catalogTest";
	public static final String GET_CATALOG_TEST_REQUEST = GET_CATALOG_TEST + "/{markerInput}";
	public static final String CREATE_RTU_USER = "/createRtuUser/{markerInputVal}/{user}";
	public static final String CREATE_RTU_USER_TEST = CREATE_RTU_USER + "/{markerInputVal}/{user}";

	public static final String INFO = "/info";
	public static final String GET_INFO = INFO + "/{revisionId}/{solutionId}";

	public static final String TEST = "/test";
	public static final String GET_TEST = TEST + "/{markerInput}/{user}";

	public static final String DEBUG = "/debug";
	public static final String DEBUG_TEST = DEBUG + "/{markerInput}/{markerInput1}/{user}";
}