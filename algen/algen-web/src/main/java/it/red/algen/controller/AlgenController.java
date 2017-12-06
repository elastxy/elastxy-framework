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

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.red.algen.applications.ApplicationService;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.stats.ExperimentStats;

@Controller
public class AlgenController {
	private static Logger logger = LoggerFactory.getLogger(AlgenController.class);


	@Autowired private InfoService infoService;
	
	@Autowired private ApplicationService applicationService;


	/**
	 * ********************* MONITOR CONTROLLER *********************
	 * TODOM: move elsewhere
	 * @return
	 */
	@RequestMapping(path = "/access", method = RequestMethod.HEAD)
	@ResponseBody
	public String access() {
		logger.info("REQUEST Service /access => (empty)");
		logger.info("RESPONSE Service /access => OK");
		return "OK";
	}
	
	
	@RequestMapping(path = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> hello() {
		logger.info("REQUEST Service /hello => (empty)");
		logger.info("RESPONSE Service /hello => Message");
		return Collections.singletonMap("message", infoService.getInfoMessage());
	}

	
	
	/**
	 * ********************* EXPERIMENT CONTROLLER *********************
	 */
	@RequestMapping(path = "/experiment/{application}", method = RequestMethod.POST)
	@ResponseBody
	public ExperimentStats experiment(
			@PathVariable String application,  
			@RequestBody AlgorithmContext context) {
		logger.info("REQUEST Service /experiment/{application} => "+application+""+context);

		context.application.appName = application;
		ExperimentStats stats = applicationService.executeExperiment(context);
		
		logger.info("RESPONSE Service /experiment/{application} => "+stats);
		return stats;
	}
	


	@RequestMapping("/test/{application}")
	@ResponseBody
	public ExperimentStats test(@PathVariable String application) {
		logger.info("REQUEST Service /test/{application} => "+application);

		ExperimentStats stats = applicationService.executeBenchmark(application);
		
		logger.info("RESPONSE Service /test/{application} => "+stats);
		return stats;
	}
	
	

	// TODOM: structured results
	@RequestMapping(path = "/analysis/{application}/{experiments}", method = RequestMethod.POST)
	@ResponseBody
	public String analysis(
			@PathVariable String application, 
			@PathVariable Integer experiments,
			@RequestBody AlgorithmContext context) {
		logger.info("REQUEST Service /analysis/{application}/{experiments} => "+application+","+experiments);
		
		context.application.appName = application;
		String result = applicationService.executeAnalysis(context, experiments);
		
		logger.info("RESPONSE Service /analysis/{domain}/{experiments} => "+result);
        return result;
	}


	@RequestMapping(path = "/trial/{application}/{experiments}", method = RequestMethod.POST)
	@ResponseBody
	public String trialTest(
			@PathVariable String application, 
			@PathVariable Integer experiments,
			@RequestBody AlgorithmContext context) {
		logger.info("REQUEST Service /trial/{application}/{experiments} => "+application+","+experiments);

		context.application.appName = application;
		String result = applicationService.executeTrialTest(context, experiments);

		logger.info("RESPONSE Service /trial/{application}/{experiments} => "+result);
        return result;
	}

}
