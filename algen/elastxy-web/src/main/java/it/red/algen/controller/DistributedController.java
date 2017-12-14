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

package it.red.algen.controller;

import java.util.Arrays;
import java.util.Base64;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.distributed.ApplicationsSparkConfig;
import it.red.algen.distributed.SparkTaskConfig;
import it.red.algen.distributed.SparkTaskExecutor;
import it.red.algen.distributed.appsupport.AlgenSparkApplication;
import it.red.algen.distributed.context.DistributedAlgorithmContext;
import it.red.algen.stats.ExperimentStats;


@Controller
@RequestMapping(path = "/distributed")
public class DistributedController {
	private static transient Logger logger = Logger.getLogger(DistributedController.class);

	
	@Autowired
	private ApplicationsSparkConfig applicationsSparkConfig;
	
	
	// TODOA-2: check status & kill jobs


    @RequestMapping(path = "/local/experiment/{application}", method = RequestMethod.POST)
    public ResponseEntity<String> executeExperimentLocal(
    		@PathVariable String application,  
			@RequestBody DistributedAlgorithmContext context) throws Exception {
    	logger.info("REQUEST Service /local/experiment/{application} => "+application+""+context);
    	
		context.application.appName = application;
		
		SparkTaskConfig taskConfig = applicationsSparkConfig.getTaskConfig(application);
		
		String master = "local[*]";
		byte[] contextBytes = ReadConfigSupport.writeJSONString(context).getBytes(); 
		String contextAsString = Base64.getEncoder().encodeToString(contextBytes);
		
		String[] params = new String[]{application, taskConfig.sparkHome, master, contextAsString};
    	logger.info("Submitting job locally with params: "+Arrays.asList(params));
		AlgenSparkApplication.main(params);

    	logger.info("RESPONSE Service /local/experiment/{application}"); // TODOA-4: return distributed ExperimentStats
        String stats = "OK";
    	return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    
    @RequestMapping(path = "/cluster/experiment/{application}", method = RequestMethod.POST)
    @ResponseBody
	public ResponseEntity<String> executeExperimentCluster(
			@PathVariable String application,  
			@RequestBody DistributedAlgorithmContext context) throws Exception {
		logger.info("REQUEST Service /cluster/experiment/{application} => "+application+""+context);
    	
		context.application.appName = application;
    	
    	SparkTaskExecutor executor = new SparkTaskExecutor();
    	String stats = executor.runDistributed(applicationsSparkConfig.getTaskConfig(application), context); // TODOA-4: return distributed ExperimentStats
    	
    	logger.info("RESPONSE Service /cluster/experiment/{application} => "+stats);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
    
    
	// TODOM-1: benchmark, analysis, trial in distributed controller
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
