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

import org.elastxy.core.applications.ApplicationService;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.context.RequestContext;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.web.renderer.InternalExperimentResponseRenderer;
import org.elastxy.web.renderer.WebExperimentResponseRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * TODOM-2: Create a UserContext before, at the end of SecurityFilterChain, 
 * to host locale and if request comes from Web.
 * @author red
 *
 */
@Controller
@RequestMapping(path = "/local")
public class LocalController {
	private static Logger logger = LoggerFactory.getLogger(LocalController.class);

	@Autowired private ApplicationService applicationService;
	
	@Autowired private WebExperimentResponseRenderer webRenderer;
	@Autowired private InternalExperimentResponseRenderer intRenderer;

	
	@RequestMapping(path = "/experiment/{application}", method = RequestMethod.POST)
	@ResponseBody
	public ExperimentResponse experiment(
			@PathVariable String application,  
			@RequestBody AlgorithmContext context,
			@RequestHeader(value="Web-Request", defaultValue="true") boolean webRequest,
			Locale userLocale) {
		logger.info("REQUEST Service /experiment/{application} => "+application+""+context);
		
		context.application.appName = application;
		context.requestContext = new RequestContext(webRequest, userLocale);
		ExperimentStats stats = applicationService.executeExperiment(context);
		logger.info("RESPONSE Service /experiment/{application} => "+stats);
		return res(webRequest, context, stats);
	}


	/**
	 * TODOM-2: a service for app-checking all registered applications
	 * @param application
	 * @param webRequest
	 * @param userLocale
	 * @return
	 */
	@RequestMapping("/appcheck/{application}")
	@ResponseBody
	public ExperimentResponse appCheck(@PathVariable String application,
			@RequestHeader(value="Web-Request", defaultValue="true") boolean webRequest,
			Locale userLocale) {
		logger.info("REQUEST Service /appcheck/{application} => "+application);

		AlgorithmContext context = new AlgorithmContext();
		context.application.appName = application;
		context.requestContext = new RequestContext(webRequest, userLocale);
		ExperimentStats stats = applicationService.executeCheck(context);
		
		logger.info("RESPONSE Service /appcheck/{application} => "+stats);
		return res(webRequest, context, stats);
	}


	@RequestMapping("/test/{application}")
	@ResponseBody
	public ExperimentResponse test(@PathVariable String application,
			@RequestHeader(value="Web-Request", defaultValue="true") boolean webRequest,
			Locale userLocale) {
		logger.info("REQUEST Service /test/{application} => "+application);

		AlgorithmContext context = new AlgorithmContext();
		context.application.appName = application;
		context.requestContext = new RequestContext(webRequest, userLocale);
		ExperimentStats stats = applicationService.executeBenchmark(context);
		
		logger.info("RESPONSE Service /test/{application} => "+stats);
		return res(webRequest, context, stats);
	}
	
	

	// TODOM-2: analysis: structured results
	@RequestMapping(path = "/analysis/{application}/{experiments}", method = RequestMethod.POST)
	@ResponseBody
	public ExperimentResponse analysis(
			@PathVariable String application, 
			@PathVariable Integer experiments,
			@RequestBody AlgorithmContext context,
			@RequestHeader(value="Web-Request", defaultValue="true") boolean webRequest,
			Locale userLocale) {
		logger.info("REQUEST Service /analysis/{application}/{experiments} => "+application+","+experiments);
		
		context.application.appName = application;
		context.requestContext = new RequestContext(webRequest, userLocale);
		String result = applicationService.executeAnalysis(context, experiments);
		
		logger.info("RESPONSE Service /analysis/{domain}/{experiments} => "+result);
		return res(webRequest, context, result);
	}


	// TODOM-2: trial: structured results
	@RequestMapping(path = "/trial/{application}/{experiments}", method = RequestMethod.POST)
	@ResponseBody
	public ExperimentResponse trialTest(
			@PathVariable String application, 
			@PathVariable Integer experiments,
			@RequestBody AlgorithmContext context,
			@RequestHeader(value="Web-Request", defaultValue="true") boolean webRequest,
			Locale userLocale) {
		logger.info("REQUEST Service /trial/{application}/{experiments} => "+application+","+experiments);

		context.application.appName = application;
		context.requestContext = new RequestContext(webRequest, userLocale);
		String result = applicationService.executeTrialTest(context, experiments);

		logger.info("RESPONSE Service /trial/{application}/{experiments} => "+result);
		return res(webRequest, context, result);
	}

	
	private ExperimentResponse res(boolean webRequest, AlgorithmContext context, ExperimentStats stats){
		return webRequest ? webRenderer.render(context, stats) : intRenderer.render(context, stats);
	}

	private ExperimentResponse res(boolean webRequest, AlgorithmContext context, String content){
		return webRequest ? webRenderer.render(context, content) : intRenderer.render(context, content);
	}

}
