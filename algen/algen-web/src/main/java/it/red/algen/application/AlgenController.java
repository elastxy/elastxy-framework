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

package it.red.algen.application;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.red.algen.controller.InfoService;
import it.red.algen.service.ExpressionsService;
import it.red.algen.stats.ExperimentStats;

@Controller
public class AlgenController {
	private static Logger logger = LoggerFactory.getLogger(AlgenController.class);


	@Autowired
	private InfoService infoService;
	
	@Autowired
	private ExpressionsService expressionsService;
	

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
//	@RequestMapping(path = "/experiment/{domain}", method = RequestMethod.POST)
//	@ResponseBody
//	public ExperimentStats calculateWithParams(
//			@PathVariable String domain,  
//			@RequestBody AlgorithmContext context) {
//		
//		contextSupplier.init(context);
//		
//	 	Experiment e = null;
//	 	if("garden".equals(domain)){
//	 		setupGardenContext(context);
//			context.monitoringConfiguration.reporter = new GardenCSVReporter(GardenApplication.STATS_DIR);
//	        e = new Experiment(gardenEnvFactory);
//	 	}
//	 	else if("expressions".equals(domain)){
//	 		setupExprContext(context);
//	 		context.monitoringConfiguration.reporter = new CSVReporter(ExprApplication.STATS_DIR);
//	        e = new Experiment(exprEnvFactory);
//	 	}
//	 	beanFactory.autowireBean(e);
//	 	
//        e.run();
//        
//        ExperimentStats stats = e.getStats();
//        
//        contextSupplier.destroy();
//        
//        return stats;
//	}
//
//
//	// TODOM: structured results
//	@RequestMapping(path = "/analysis/{domain}/{experiments}", method = RequestMethod.POST)
//	@ResponseBody
//	public String analysis(
//			@PathVariable String domain, 
//			@PathVariable Integer experiments,
//			@RequestBody AlgorithmContext context) {
//		
//		contextSupplier.init(context);
//		
//	 	EnvFactory envFactory = null;
//	 	
//	 	// TODOM: Make generics default build mode
//	 	if("garden".equals(domain)){
//	 		setupGardenContext(context);
//	 		envFactory = gardenEnvFactory;
//	 	}
//	 	else if("expressions".equals(domain)){ 
//	 		setupExprContext(context);
//	 		envFactory = exprEnvFactory;
//	 	}
//
//        StatsExperimentExecutor collector = new StatsExperimentExecutor(envFactory, experiments);
//        beanFactory.autowireBean(collector);
//        
//        collector.run();
//        
//        contextSupplier.destroy();
//        
//        String result = collector.print();
//        return result;
//	}

//
//	private void setupGardenContext(AlgorithmContext context) {
//		context.fitnessCalculator = new GardenFitnessCalculator();
//		context.selector = new StandardSelector();
//		context.selector.setup(context.parameters);
//		context.mutator = new GardenMutator();
//		context.recombinator = new GardenRecombinator();
//	}
//
//	private void setupExprContext(AlgorithmContext context) {
//		context.fitnessCalculator = new ExprFitnessCalculator();
//		context.selector = new StandardSelector();
//		context.selector.setup(context.parameters);
//		context.mutator = new ExprMutator();
//		context.mutator.setGenesFactory(exprGenesFactory);
//		context.recombinator = new ExprRecombinator();
//	}

	
	@RequestMapping("/test/{domain}")
	@ResponseBody
	public ExperimentStats calculateTest(@PathVariable String domain) {
		logger.info("REQUEST Service /test/{domain} => "+domain);

		ExperimentStats stats = null;
		if("expressions".equals(domain)){ 
		 	stats = expressionsService.executeBenchmark();
		}
		logger.info("RESPONSE Service /test/{domain} => "+stats);
		return stats;
	}


//	@RequestMapping(path = "/trial/{domain}/{experiments}", method = RequestMethod.POST)
//	@ResponseBody
//	public String trialTest(
//			@PathVariable String domain, 
//			@PathVariable Integer experiments,
//			@RequestBody AlgorithmContext context) {
//		
//		contextSupplier.init(context);
//		
//	 	EnvFactory envFactory = null;
//	 	
//	 	// TODOM: Make generics default build mode
//	 	if("garden".equals(domain)){
//	 		setupGardenContext(context);
//	 		envFactory = gardenEnvFactory;
//	 	}
//	 	else if("expressions".equals(domain)){ 
//	 		setupExprContext(context);
//	 		context.parameters.elitarism = false;
//	 		context.parameters.mutationPerc = 0.0;
//	 		context.parameters.recombinationPerc = 0.0;
//	 		context.parameters.initialSelectionRandom = true;
//	 		context.selector = new UniformlyDistributedSelector();
//			context.selector.setup(context.parameters, populationFactory);
//
//	 		envFactory = exprEnvFactory;
//	 	}
//
//        StatsExperimentExecutor collector = new StatsExperimentExecutor(envFactory, experiments);
//        beanFactory.autowireBean(collector);
//        
//        collector.run();
//        
//        contextSupplier.destroy();
//        
//        String result = collector.print();
//        return result;
//	}

}
