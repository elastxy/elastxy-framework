package it.red.algen.distributed;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.github.ywilkof.sparkrestclient.DriverState;
import com.github.ywilkof.sparkrestclient.SparkRestClient;

import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.distributed.context.DistributedAlgorithmContext;

public class SparkTaskExecutor {
	private static Logger logger = Logger.getLogger(SparkTaskExecutor.class);

	// TODOD: check status & kill services

    public String runDistributed(SparkTaskConfig config, DistributedAlgorithmContext context) throws Exception {

    	// Setup configurations
    	logger.info("Task Configuration");
    	logger.info(config.toString());
    	
		byte[] contextBytes = ReadConfigSupport.writeJSONString(context).getBytes(); 
		String contextAsString = Base64.getEncoder().encodeToString(contextBytes);
    	
    	// Create client
    	logger.info("Creating client..");
    	final Map<String,String> environmentVariables = new HashMap<>();
    	environmentVariables.put("log4j.configuration",				config.log4jConfiguration);
    	environmentVariables.put("spark.eventLog.enabled",			config.historyEventsEnabled);
    	environmentVariables.put("spark.eventLog.dir",				config.historyEventsDir);
    	environmentVariables.put("spark.history.fs.logDirectory",	config.historyEventsDir);
    	logger.info("Client config: "+Arrays.asList(config.masterHost, config.sparkVersion, environmentVariables));
    	final SparkRestClient sparkClient = SparkRestClient.builder()
        	.masterHost(config.masterHost)
        	.sparkVersion(config.sparkVersion)
        	.environmentVariables(environmentVariables)
        	.build();
    	logger.info("Client created on API root: "+sparkClient.getMasterApiRoot());
    	
    	// Submit job
    	logger.info("Submitting remote job..");
    	Set<String> otherJarsPath = config.otherJarsPath==null ? null : new HashSet<String>(Arrays.asList(config.otherJarsPath.split(",")));
    	List<String> params = Arrays.asList(config.appName, config.sparkHome, config.masterURI, contextAsString);
    	logger.info("Job params: "+params);
    	logger.info("Other client params: "+Arrays.asList(config.appJarPath, config.mainClass, otherJarsPath));

    	final String submissionId = sparkClient.prepareJobSubmit()
    		    .appName(config.appName)
    		    .appResource(config.appJarPath)
    		    .mainClass(config.mainClass)
    		    .usingJars(otherJarsPath)
    		    .appArgs(params)
    		    .withProperties()
		    	.put("log4j.configuration", config.log4jConfiguration)
		    	.put("spark.eventLog.enabled", config.historyEventsEnabled)
		    	.put("spark.eventLog.dir", config.historyEventsDir)
		    	.put("spark.history.fs.logDirectory", config.historyEventsDir)
    		.submit();
    	logger.info("Job submitted, with id: "+submissionId);
    	
    	// Check status
    	logger.info("Checking status every 5 seconds or so..");
    	List<DriverState> endedStates = Arrays.asList(
    			DriverState.ERROR,
    			DriverState.FAILED,
    			DriverState.FINISHED,
    			DriverState.KILLED,
    			DriverState.NOT_FOUND
    			);
    	DriverState driverState = null;
    	while (true) {
    		 driverState = 
    				 sparkClient
    				 .checkJobStatus()
    				 .withSubmissionId(submissionId);
    		 logger.info("Status: "+driverState);
             Thread.sleep(5 * 1000);
             if(endedStates.contains(driverState)){
            	 logger.info("Job ended with state: "+driverState);
            	 break;
             }
         }
    	return driverState.toString();
    }
    
}
