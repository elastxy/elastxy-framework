package org.elastxy.web.distributed;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.support.JSONSupport;
import org.elastxy.distributed.context.DistributedAlgorithmContext;
import org.elastxy.web.controller.ExperimentResponse;
import org.elastxy.web.renderer.InternalExperimentResponseRenderer;
import org.elastxy.web.renderer.WebExperimentResponseRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ywilkof.sparkrestclient.DriverState;
import com.github.ywilkof.sparkrestclient.SparkRestClient;

@Component
public class SparkTaskExecutor {
	private static Logger logger = Logger.getLogger(SparkTaskExecutor.class);

	
	@Autowired private WebExperimentResponseRenderer webRenderer;
	@Autowired private InternalExperimentResponseRenderer intRenderer;

	
    public String runDistributed(SparkTaskConfig config, DistributedAlgorithmContext context) throws Exception {

    	// Setup configurations
    	logger.info("Task Configuration");
    	logger.info(config.toString());
    	
		byte[] contextBytes = JSONSupport.writeJSONString(context).getBytes(); 
		String contextAsString = Base64.getEncoder().encodeToString(contextBytes);
    	
    	// Create client
    	logger.info("Creating client..");
    	// TODOA-2: inject task configurations, or get from local properties
//    	final Map<String,String> environmentVariables = new HashMap<>();
//    	environmentVariables.put("log4j.configuration",				config.log4jConfiguration);
//    	environmentVariables.put("spark.eventLog.enabled",			config.historyEventsEnabled);
//    	environmentVariables.put("spark.eventLog.dir",				config.historyEventsDir);
//    	environmentVariables.put("spark.history.fs.logDirectory",	config.historyEventsDir);
//    	logger.info("Client config: "+Arrays.asList(config.masterHost, config.sparkVersion, environmentVariables));
    	logger.info("Client config: "+Arrays.asList(config.masterHost, config.sparkVersion));
    	final SparkRestClient sparkClient = SparkRestClient.builder()
        	.masterHost(config.masterHost)
        	.sparkVersion(config.sparkVersion)
//        	.environmentVariables(environmentVariables)
        	.build();
    	logger.info("Client created on API root: "+sparkClient.getMasterApiRoot());
    	
    	// Submit job
    	logger.info("Submitting remote job with taskIdentifier ["+config.taskIdentifier+"]");
    	Set<String> otherJarsPath = config.otherJarsPath==null ? null : new HashSet<String>(Arrays.asList(config.otherJarsPath.split(",")));
    	List<String> params = Arrays.asList(
    			config.appName, 
    			config.taskIdentifier,
    			config.sparkHome, 
    			config.outputPath,
    			config.masterURI, 
    			contextAsString);
    	logger.info("Job params: "+params);
    	logger.info("Other client params: "+Arrays.asList(config.appJarPath, config.mainClass, otherJarsPath));

    	final String submissionId = sparkClient.prepareJobSubmit()
    		    .appName(config.appName)
    		    .appResource(config.appJarPath)
    		    .mainClass(config.mainClass)
    		    .usingJars(otherJarsPath)
    		    .appArgs(params)
//    		    .withProperties()
//		    	.put("log4j.configuration", config.log4jConfiguration)
//		    	.put("spark.eventLog.enabled", config.historyEventsEnabled)
//		    	.put("spark.eventLog.dir", config.historyEventsDir)
//		    	.put("spark.history.fs.logDirectory", config.historyEventsDir)
    		.submit();
    	logger.info("Job submitted, with id: "+submissionId);
    	
    	// Check status
    	logger.info("Checking status every 5 seconds or so.. (max 1 hour)");
    	List<DriverState> endedStates = Arrays.asList(
    			DriverState.ERROR,
    			DriverState.FAILED,
    			DriverState.FINISHED,
    			DriverState.KILLED,
    			DriverState.NOT_FOUND
    			);
    	DriverState driverState = null;
    	int timeIntervals = 0;
    	while (true) {
    		 driverState = 
    				 sparkClient
    				 .checkJobStatus()
    				 .withSubmissionId(submissionId);
    		 logger.info("Status: "+driverState);
             Thread.sleep(5 * 1000);
             if(endedStates.contains(driverState)){
            	 logger.info("Job ended correctly. State: "+driverState);
            	 break;
             }
             else if(timeIntervals++ > 720){ // at most 720*5 = 3600 seconds (1 hour)
            	 logger.info("Timeout after 1 hour without ending execution. Last state: "+driverState);
            	 break;
             }
         }
    	return driverState.toString();
    }
    

    
    // TODOB-1: remove duplication
	private ExperimentResponse res(boolean webRequest, AlgorithmContext context, ExperimentStats stats){
		return webRequest ? webRenderer.render(context, stats) : intRenderer.render(context, stats);
	}

	private ExperimentResponse res(boolean webRequest, AlgorithmContext context, String content){
		return webRequest ? webRenderer.render(context, content) : intRenderer.render(context, content);
	}
    
}
