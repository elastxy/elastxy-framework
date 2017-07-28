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
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;
import it.red.algen.engine.EnvFactory;
import it.red.algen.expressions.conf.ExprConf;
import it.red.algen.expressions.context.ExprBenchmark;
import it.red.algen.expressions.engine.ExprEnvFactory;
import it.red.algen.garden.conf.GardenConf;
import it.red.algen.garden.context.GardenBenchmark;
import it.red.algen.garden.engine.GardenEnvFactory;
import it.red.algen.garden.tracking.GardenCSVReporter;
import it.red.algen.stats.Experiment;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.stats.StatsExperimentExecutor;
import it.red.algen.tracking.CSVReporter;

@Controller
public class SampleController {
	private static Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private ContextSupplier contextSupplier;
	
	@Autowired
	private InfoService infoService;

	
	@Autowired
	private ExprEnvFactory exprEnvFactory;
	
	@Autowired
	private GardenEnvFactory gardenEnvFactory;
	
	
	private @Autowired AutowireCapableBeanFactory beanFactory;

	
	
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

	
	@RequestMapping(path = "/experiment/{domain}", method = RequestMethod.POST)
	@ResponseBody
	public ExperimentStats calculateWithParams(
			@PathVariable String domain,  
			@RequestBody AlgorithmContext context) {
		
		contextSupplier.init(context);
		
	 	Experiment e = null;
	 	if("garden".equals(domain)){
			context.monitoringConfiguration.reporter = new GardenCSVReporter(GardenConf.STATS_DIR);
	        e = new Experiment(gardenEnvFactory);
	 	}
	 	else if("expressions".equals(domain)){
	 		context.monitoringConfiguration.reporter = new CSVReporter(ExprConf.STATS_DIR);
	        e = new Experiment(exprEnvFactory);
	 	}
	 	beanFactory.autowireBean(e);
	 	
        e.run();
        
        ExperimentStats stats = e.getStats();
        
        contextSupplier.destroy();
        
        return stats;
	}


	// TODOM: structured results
	@RequestMapping(path = "/analysis/{domain}/{experiments}", method = RequestMethod.POST)
	@ResponseBody
	public String analysis(
			@PathVariable String domain, 
			@PathVariable Integer experiments,
			@RequestBody AlgorithmContext context) {
		
		contextSupplier.init(context);
		
	 	EnvFactory envFactory = null;
	 	
	 	// TODOM: Make generics default build mode
	 	if("garden".equals(domain)){ 
	 		envFactory = gardenEnvFactory;
	 	}
	 	else if("expressions".equals(domain)){ 
	 		envFactory = exprEnvFactory;
	 	}

        StatsExperimentExecutor collector = new StatsExperimentExecutor(envFactory, experiments);
        beanFactory.autowireBean(collector);
        
        collector.run();
        
        String result = collector.print();
        return result;
	}

	
	@RequestMapping("/test/{domain}")
	@ResponseBody
	public ExperimentStats calculateTest(@PathVariable String domain) {
		 	AlgorithmContext context = null;
		 	EnvFactory envFactory = null;
		 	
		 	// TODOM: Make generics default build mode
		 	if("garden".equals(domain)){ 
		 		context = new GardenBenchmark().build(); 
		 		envFactory = gardenEnvFactory;
		 	}
		 	else if("expressions".equals(domain)){ 
		 		context = new ExprBenchmark().build(); 
		 		envFactory = exprEnvFactory;
		 	}
			
		 	contextSupplier.init(context);
			Gson gson = new Gson();
			String json = gson.toJson(context);
			logger.info(json);
			
			Experiment e = new Experiment(envFactory);
		 	beanFactory.autowireBean(e);
	        e.run();
	        
	        ExperimentStats stats = e.getStats();
	        
	        contextSupplier.destroy();
	        
	        return stats;
	}



}
