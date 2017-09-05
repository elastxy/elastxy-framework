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

import it.red.algen.algofrigerator.AlgofrigeratorService;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.metaexpressions.ExpressionsService;
import it.red.algen.metagarden.GardenService;
import it.red.algen.metasudoku.SudokuService;
import it.red.algen.stats.ExperimentStats;

@Controller
public class AlgenController {
	private static Logger logger = LoggerFactory.getLogger(AlgenController.class);


	@Autowired private InfoService infoService;
	
	@Autowired private ExpressionsService expressionsService;

	@Autowired private GardenService gardenService;

	@Autowired private SudokuService sudokuService;

	@Autowired private AlgofrigeratorService algofrigeratorService;
	

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
	@RequestMapping(path = "/experiment/{domain}", method = RequestMethod.POST)
	@ResponseBody
	public ExperimentStats experiment(
			@PathVariable String domain,  
			@RequestBody AlgorithmContext context) {
		logger.info("REQUEST Service /experiment/{domain} => "+domain+""+context);

		ExperimentStats stats = null;
		if("expressions".equals(domain)){ 
		 	stats = expressionsService.executeExperiment(context);
		}
		else if("garden".equals(domain)){ 
		 	stats = gardenService.executeExperiment(context);
		}
		else if("sudoku".equals(domain)){ 
		 	stats = sudokuService.executeExperiment(context);
		}
		else if("algofrigerator".equals(domain)){ 
		 	stats = algofrigeratorService.executeExperiment(context);
		}
		logger.info("RESPONSE Service /experiment/{domain} => "+stats);
		return stats;
	}
	


	@RequestMapping("/test/{domain}")
	@ResponseBody
	public ExperimentStats test(@PathVariable String domain) {
		logger.info("REQUEST Service /test/{domain} => "+domain);

		ExperimentStats stats = null;
		if("expressions".equals(domain)){ 
		 	stats = expressionsService.executeBenchmark();
		}
		else if("garden".equals(domain)){ 
		 	stats = gardenService.executeBenchmark();
		}
		else if("sudoku".equals(domain)){ 
		 	stats = sudokuService.executeBenchmark();
		}
		else if("algofrigerator".equals(domain)){ 
		 	stats = algofrigeratorService.executeBenchmark();
		}
		logger.info("RESPONSE Service /test/{domain} => "+stats);
		return stats;
	}
	
	

	// TODOM: structured results
	@RequestMapping(path = "/analysis/{domain}/{experiments}", method = RequestMethod.POST)
	@ResponseBody
	public String analysis(
			@PathVariable String domain, 
			@PathVariable Integer experiments,
			@RequestBody AlgorithmContext context) {
		logger.info("REQUEST Service /analysis/{domain}/{experiments} => "+domain+","+experiments);
		String result = null;
	 	if("expressions".equals(domain)){
	 		result = expressionsService.executeAnalysis(context, experiments);
	 	}
	 	else if("garden".equals(domain)){
	 		result = gardenService.executeAnalysis(context, experiments);
	 	}
		else if("sudoku".equals(domain)){ 
			result = sudokuService.executeAnalysis(context, experiments);
		}
		else if("algofrigerator".equals(domain)){ 
			result = algofrigeratorService.executeAnalysis(context, experiments);
		}
		logger.info("RESPONSE Service /analysis/{domain}/{experiments} => "+result);
        return result;
	}


	@RequestMapping(path = "/trial/{domain}/{experiments}", method = RequestMethod.POST)
	@ResponseBody
	public String trialTest(
			@PathVariable String domain, 
			@PathVariable Integer experiments,
			@RequestBody AlgorithmContext context) {
		logger.info("REQUEST Service /trial/{domain}/{experiments} => "+domain+","+experiments);
		String result = null;
	 	if("expressions".equals(domain)){
	 		result = expressionsService.executeTrialTest(context, experiments);
	 	}
	 	else if("garden".equals(domain)){
	 		result = gardenService.executeTrialTest(context, experiments);
	 	}
		else if("sudoku".equals(domain)){ 
			result = sudokuService.executeTrialTest(context, experiments);
		}
		else if("algofrigerator".equals(domain)){ 
			result = algofrigeratorService.executeTrialTest(context, experiments);
		}
		logger.info("RESPONSE Service /trial/{domain}/{experiments} => "+result);
        return result;
	}

}
