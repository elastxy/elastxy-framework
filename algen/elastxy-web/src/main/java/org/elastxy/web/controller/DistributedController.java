/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.elastxy.web.controller;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.elastxy.core.context.RequestContext;
import org.elastxy.distributed.context.DistributedAlgorithmContext;
import org.elastxy.web.distributed.DistributedApplicationService;
import org.elastxy.web.distributed.SparkJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Services for executing experiments in a distributed fashion.
 * 
 * Path cluster or local is related to Spark execution: local is on the cores of same JVM.
 * 
 * TODO2-2: asynchronous fashion services: experiment id returned and results collected after
 * TODO2-2: enable swagger
 * 
 * @author red
 *
 */
@Controller
@RequestMapping(path = "/distributed")
public class DistributedController {
	private static transient Logger logger = Logger.getLogger(DistributedController.class);

	@Autowired private DistributedApplicationService applicationService;

    
    @RequestMapping(path = "/local/experiment/{application}", method = RequestMethod.POST)
	@ResponseBody
    public ExperimentResponse executeExperimentLocal(
    		@PathVariable String application,  
			@RequestBody DistributedAlgorithmContext context,
			@RequestHeader(value="Web-Request", defaultValue="true") boolean webRequest,
			Locale userLocale) throws Exception {
    	logger.info("REQUEST Service /local/experiment/{application} => "+application+""+context);

		context.application.appName = application;
		context.requestContext = new RequestContext(webRequest, userLocale);
		
		ExperimentResponse response = applicationService.executeDistributedLocal(context);
		
    	logger.info("RESPONSE Service /local/experiment/{application} => "+response.status);
    	return response;
    }

    
    @RequestMapping(path = "/cluster/experiment/{application}", method = RequestMethod.POST)
    @ResponseBody
	public ExperimentResponse executeExperimentCluster(
			@PathVariable String application,  
			@RequestBody DistributedAlgorithmContext context,
			@RequestHeader(value="Web-Request", defaultValue="true") boolean webRequest,
			Locale userLocale) throws Exception {
		logger.info("REQUEST Service /cluster/experiment/{application} => "+application+""+context);
    	
		context.application.appName = application;
		context.requestContext = new RequestContext(webRequest, userLocale);

		ExperimentResponse response = applicationService.executeDistributedCluster(context);

    	logger.info("RESPONSE Service /cluster/experiment/{application} => "+response.status);
    	return response;
    }
    
    
	// TODO1-1: check, benchmark, analysis, trial in distributed controller
//	@RequestMapping("/local/test/{application}")
//	@ResponseBody
//	public ResponseEntity<String> testLocal(@PathVariable String application) {
//		logger.info("REQUEST Service /local/test/{application} => "+application);
//		String stats = null;
//		
//		logger.info("RESPONSE Service /local/test/{application} => "+stats);
//		return new ResponseEntity<>(stats, HttpStatus.OK);
//	}
//
//	@RequestMapping("/cluster/test/{application}")
//	@ResponseBody
//	public ResponseEntity<String> testCluster(@PathVariable String application) {
//		logger.info("REQUEST Service /cluster/test/{application} => "+application);
//		String stats = null;
//		
//		logger.info("RESPONSE Service /cluster/test/{application} => "+stats);
//		return new ResponseEntity<>(stats, HttpStatus.OK);
//	}


}
