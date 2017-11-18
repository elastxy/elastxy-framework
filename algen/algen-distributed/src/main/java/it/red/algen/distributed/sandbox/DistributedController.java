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

package it.red.algen.distributed.sandbox;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/distributed")
public class DistributedController {

	@Autowired
	private SparkHeartbeatTask sparkHeartbeatTask;
	

	@Autowired
	private ConcurrentTask evolveTask;

	@Value("${master.uri}")
	private String masterUri;

	@Value("${master.host}")
	private String masterHost;

	@Value("${spark.version}")
	private String sparkVersion;

	@Value("${jars.path}")
	private String jarsPath;

	@Value("${spark.log4j.configuration}")
	private String sparkLog4jConfiguration;

	@Value("${spark.history.events.enabled}")
	private String sparkHistoryEventsEnabled;

	@Value("${spark.history.events.path}")
	private String sparkHistoryEventsPath;

	
	@RequestMapping(path = "/access", method = RequestMethod.HEAD)
	@ResponseBody
	public String access() {
		return "OK";
	}

    @RequestMapping("/test/spark/distributed")
    public ResponseEntity<String> testSparkDistributed() throws Exception {
    	
    	SparkJobConfig config = new SparkJobConfig();
    	config.masterURI = masterUri;
    	config.masterHost = masterHost;
    	config.sparkVersion = sparkVersion;    	
    	config.log4jConfiguration = sparkLog4jConfiguration;
    	config.historyEventsEnabled = sparkHistoryEventsEnabled;
    	config.historyEventsDir = "file:///"+sparkHistoryEventsPath;

    	config.appName = "MexApplication";
    	config.appJar = "file:///"+jarsPath+"Scaligen-assembly-1.0.jar";
    	config.mainClass = "it.red.algen.d.mex.MexApplication";

    	String result = sparkHeartbeatTask.runDistributed(config);
    	
//    	String result = sparkHeartbeatTask.runDistributed();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
	
    @RequestMapping("/test/spark/single")
    public ResponseEntity<String> testSparkSingle() {
    	String result = sparkHeartbeatTask.runSingle("C://tmp//algendata//words.txt");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    

    @RequestMapping("/test/spark/concurrent")
    public ResponseEntity<String> testSparkConcurrent() throws ExecutionException, InterruptedException {
    	String result = sparkHeartbeatTask.runConcurrent("C://tmp//algendata//words1.txt", "C://tmp//algendata//words2.txt");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @RequestMapping("/evolution/experiment/{application}")
    public ResponseEntity<String> evolution(@PathVariable String application) throws ExecutionException, InterruptedException {
    	String result = evolveTask.run(application);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    
    

    @RequestMapping("/test/spark/distributed/mexd")
    public ResponseEntity<String> testSparkDistributedMexd() throws Exception {
    	
    	SparkJobConfig config = new SparkJobConfig();
    	config.masterURI = masterUri;
    	config.masterHost = masterHost;
    	config.sparkVersion = sparkVersion;    	
    	config.log4jConfiguration = sparkLog4jConfiguration;
    	config.historyEventsEnabled = sparkHistoryEventsEnabled;
    	config.historyEventsDir = "file:///"+sparkHistoryEventsPath;

    	config.appName = "expressions.d";
    	config.appJar = "file:///"+jarsPath+"algen-applications-1.0.0-SNAPSHOT.jar";
    	config.mainClass = "it.red.algen.d.metaexpressions.SparkApplication";

    	String result = sparkHeartbeatTask.runDistributed(config);
    	
//    	String result = sparkHeartbeatTask.runDistributed();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    

}
