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

package it.red.algen.actuator.log4j;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.red.algen.AlgParameters;
import it.red.algen.expressions.ExprConf;
import it.red.algen.expressions.ExprEnvFactory;
import it.red.algen.garden.GardenCSVReporter;
import it.red.algen.garden.GardenConf;
import it.red.algen.garden.GardenEnvFactory;
import it.red.algen.stats.Experiment;
import it.red.algen.stats.Stats;
import it.red.algen.tracking.CSVReporter;
import it.red.algen.tracking.LoggerManager;
import it.red.algen.tracking.SimpleLogger;

@Controller
public class SampleController {

	@Autowired
	private HelloWorldService helloWorldService;

	@RequestMapping("/")
	@ResponseBody
	public Map<String, String> helloWorld() {
		return Collections.singletonMap("message",
				this.helloWorldService.getHelloMessage());
	}

	@RequestMapping("/foo")
	@ResponseBody
	public String foo() {
		throw new IllegalArgumentException("Server error");
	}
	

	@RequestMapping("/calculate/{domain}/{target}")
	@ResponseBody
	public Stats calculate(@PathVariable String domain, @PathVariable Integer target) {
		 	LoggerManager.instance().init(new SimpleLogger());
		 	Experiment e = null;
		 	if("garden".equals(domain)){
		        AlgParameters.instance().init(
		        		GardenConf.RECOMBINANTION_PERC, 
		        		GardenConf.MUTATION_PERC, 
		        		GardenConf.ELITARISM);
		        e = new Experiment(
		        		new GardenEnvFactory(), 
		        		GardenConf.MAX_ITERATIONS, 
		        		GardenConf.MAX_LIFETIME_SEC, 
		        		GardenConf.MAX_IDENTICAL_FITNESSES,
		        		GardenConf.VERBOSE,
		        		new GardenCSVReporter(GardenConf.STATS_DIR));
		 	}
		 	else if("expressions".equals(domain)){
		        AlgParameters.instance().init(
		        		ExprConf.RECOMBINANTION_PERC, 
		        		ExprConf.MUTATION_PERC, 
		        		ExprConf.ELITARISM);
		        e = new Experiment(
		        		new ExprEnvFactory(target), 
		        		ExprConf.MAX_ITERATIONS, 
		        		ExprConf.MAX_LIFETIME_SEC, 
		        		ExprConf.MAX_IDENTICAL_FITNESSES,
		        		ExprConf.VERBOSE, 
		        		new CSVReporter(ExprConf.STATS_DIR));
		 	}
	        e.run();
	        Stats stats = e.getStats();
	        return stats;
	}
	
}
