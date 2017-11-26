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

package it.red.algen.distributed;

import java.util.Arrays;
import java.util.Base64;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.d.metaexpressions.MexdSparkApplication;
import it.red.algen.distributed.context.DistributedAlgorithmContext;


/**
 * TODOA: Move Healthcheck to another specific Controller
 * @author red
 */
@Controller
@RequestMapping(path = "/distributed")
public class DistributedController {
	private static transient Logger logger = Logger.getLogger(DistributedController.class);

	
	
	@Autowired
	private SparkConf sparkConfLocal;
	
	@Autowired
	private SparkConf sparkConfRemote;

	@Value("${test.file.path}")
	private String testFilePath;
	
	
	
	// TODOA: mettere ordine!
	@Value("${spark.home}")
	private String sparkHome;
	
	@Value("${master.uri}")
	private String masterUri;

	@Value("${master.host}")
	private String masterHost;

	@Value("${spark.version}")
	private String sparkVersion;

	@Value("${app.jar.path}")
	private String appJarPath;

	@Value("${main.class}")
	private String mainClass;
	
	@Value("${other.jars.path}")
	private String otherJarsPath;

	@Value("${spark.log4j.configuration}")
	private String sparkLog4jConfiguration;

	@Value("${spark.eventLog.enabled}")
	private String sparkHistoryEventsEnabled;

	@Value("${spark.eventLog.dir}")
	private String sparkHistoryEventsPath;

//	@Value("${spark.history.fs.logDirectory}")
//	private String sparkHistoryEventsLogdir;

	
	@RequestMapping(path = "/access", method = RequestMethod.HEAD)
	@ResponseBody
	public String access() {
		return "OK";
	}

	
    // TODOA: remove or turn into an in memory check or add distributed test
    @RequestMapping("/healthcheck")
    public ResponseEntity<String> healthCheck() {
    	String localResult = runHealthCheck(sparkConfLocal);
    	String remoteResult = runHealthCheck(sparkConfRemote);
        return new ResponseEntity<>("LOCAL: \n"+localResult+"\nREMOTE: \n"+remoteResult, HttpStatus.OK);
    }


	private String runHealthCheck(SparkConf sparkConf) {
		String result;
		JavaSparkContext context = new JavaSparkContext(sparkConf);
		try {
    		result = SparkHealthCheckTask.run(context, testFilePath);
    	}
    	catch(Throwable t){
    		result = "Error calling healthcheck! Ex: "+t;
    		logger.fatal(result, t);
    	}
    	finally {
    		context.stop();
    	}
		return result;
	}

	// TODOD: check status & kill jobs

    

    @RequestMapping(path = "/experiment/local/{application}", method = RequestMethod.POST)
    public ResponseEntity<String> executeExperimentLocal(
    		@PathVariable String application,  
			@RequestBody DistributedAlgorithmContext context) throws Exception {
    	logger.info("REQUEST Service /experiment/local/{application} => "+application+""+context);
    	
		context.application.name = application;
		
		String sparkHome = this.sparkHome; // "C:/dev/spark-2.2.0-bin-hadoop2.7"
		String master = "local[*]"; // local[*] | spark://192.168.1.101:7077
		byte[] contextBytes = ReadConfigSupport.writeJSONString(context).getBytes(); 
		String contextAsString = Base64.getEncoder().encodeToString(contextBytes);
		
		String[] params = new String[]{application, sparkHome, master, contextAsString};
    	logger.info("Submitting job locally with params: "+Arrays.asList(params));
		MexdSparkApplication.main(params);

    	logger.info("RESPONSE Service /experiment/local/{application}"); // TODOD: results
        String stats = "OK";
    	return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    
    @RequestMapping(path = "/experiment/cluster/{application}", method = RequestMethod.POST)
    @ResponseBody
	public ResponseEntity<String> executeExperimentCluster(
			@PathVariable String application,  
			@RequestBody DistributedAlgorithmContext context) throws Exception {
		logger.info("REQUEST Service /experiment/cluster/{application} => "+application+""+context);
    	
		context.application.name = application;
		
    	SparkTaskConfig config = new SparkTaskConfig();
    	config.masterURI = masterUri;
    	config.masterHost = masterHost;
    	config.sparkVersion = sparkVersion;    	
    	config.sparkHome = sparkHome;
    	config.log4jConfiguration = sparkLog4jConfiguration;
    	
    	config.historyEventsEnabled = sparkHistoryEventsEnabled;
    	config.historyEventsDir = sparkHistoryEventsPath;

    	config.appName = application;
    	config.appJarPath = appJarPath;
    	config.mainClass = mainClass;
    	config.otherJarsPath = otherJarsPath;
    	
    	SparkTaskExecutor executor = new SparkTaskExecutor();
    	String stats = executor.runDistributed(config, context); // TODOD: ExperimentStats
    	
    	logger.info("RESPONSE Service /experiment/cluster/{application} => "+stats);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
    
    
	


//	@RequestMapping("/test/cluster/{application}")
//	@ResponseBody
//	public ExperimentStats test(@PathVariable String application) {
//		logger.info("REQUEST Service /test/cluster/{application} => "+application);
//
//		// TODOD
//		if(true) throw new UnsupportedOperationException("NYI");
//		String stats = "N/A";
//		logger.info("RESPONSE Service /test/cluster/{application} => "+stats);
//		return stats;
//	}
    
    

}
