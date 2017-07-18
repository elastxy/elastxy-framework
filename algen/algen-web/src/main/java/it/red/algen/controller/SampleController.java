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

import com.google.gson.Gson;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.expressions.ExprConf;
import it.red.algen.expressions.ExprEnvFactory;
import it.red.algen.expressions.ExprTarget;
import it.red.algen.garden.GardenCSVReporter;
import it.red.algen.garden.GardenConf;
import it.red.algen.garden.GardenEnvFactory;
import it.red.algen.stats.Experiment;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.CSVReporter;
import it.red.algen.tracking.LoggerManager;
import it.red.algen.tracking.SimpleLogger;

@Controller
public class SampleController {
	private static Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private InfoService infoService;

	
	// TODOA: refactor! ExprEnvFactory cannot be a Singleton
	@Autowired
	private ExprEnvFactory exprEnvFactory;
	
	// TODOA: refactor!
	@Autowired
	private GardenEnvFactory gardenEnvFactory;
	
	
	@RequestMapping(path = "/access", method = RequestMethod.HEAD)
	@ResponseBody
	public String access() {
		return "OK";
	}
	
	@RequestMapping(path = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> hello() {
		return Collections.singletonMap("message",
				infoService.getInfoMessage());
	}

	
	@RequestMapping(path = "/calculate/{domain}/{target}", method = RequestMethod.POST)
	@ResponseBody
	public ExperimentStats calculateWithParams(
			@PathVariable String domain, 
			@PathVariable Integer target, 
			@RequestBody AlgorithmContext context) {
	 	LoggerManager.instance().init(new SimpleLogger());
	 	Experiment e = null;
	 	if("garden".equals(domain)){
			context.monitoringConfiguration.reporter = new GardenCSVReporter(GardenConf.STATS_DIR);
	        e = new Experiment(context,null,gardenEnvFactory);
	 	}
	 	else if("expressions".equals(domain)){
	 		context.monitoringConfiguration.reporter = new CSVReporter(ExprConf.STATS_DIR);
	        e = new Experiment(context,new ExprTarget(target),exprEnvFactory);
	 	}
        e.run();
        ExperimentStats stats = e.getStats();
        return stats;
	}

	
	
	@RequestMapping("/calculate-test/{domain}/{target}")
	@ResponseBody
	public ExperimentStats calculate(@PathVariable String domain, @PathVariable Integer target) {
		 	LoggerManager.instance().init(new SimpleLogger());
		 	Experiment e = null;
		 	if("garden".equals(domain)){
				AlgorithmContext context = AlgorithmContext.build(
						GardenConf.INITIAL_SELECTION_NUMBER,
						GardenConf.INITIAL_SELECTION_RANDOM,
						GardenConf.RECOMBINANTION_PERC, 
		        		GardenConf.MUTATION_PERC, 
		        		GardenConf.ELITARISM, 
		        		GardenConf.MAX_ITERATIONS, 
		        		GardenConf.MAX_LIFETIME_SEC, 
		        		GardenConf.MAX_IDENTICAL_FITNESSES,
		        		GardenConf.VERBOSE, 
		        		new GardenCSVReporter(GardenConf.STATS_DIR));

				Gson gson = new Gson();
				String json = gson.toJson(context);
				logger.info(json);
				
		        e = new Experiment(
		        		context,
		        		null,
		        		gardenEnvFactory);
		 	}
		 	else if("expressions".equals(domain)){
				AlgorithmContext context = AlgorithmContext.build(
						GardenConf.INITIAL_SELECTION_NUMBER,
						GardenConf.INITIAL_SELECTION_RANDOM,
						ExprConf.RECOMBINANTION_PERC, 
		        		ExprConf.MUTATION_PERC, 
		        		ExprConf.ELITARISM, 
		        		ExprConf.MAX_ITERATIONS, 
		        		ExprConf.MAX_LIFETIME_SEC, 
		        		ExprConf.MAX_IDENTICAL_FITNESSES,
		        		ExprConf.VERBOSE, 
		        		new CSVReporter(ExprConf.STATS_DIR));

				Gson gson = new Gson();
				String json = gson.toJson(context);
				logger.info(json);
				
		        e = new Experiment(
		        		context,
		        		new ExprTarget(target),
		        		exprEnvFactory);
		 	}
	        e.run();
	        ExperimentStats stats = e.getStats();
	        return stats;
	}
	
}
