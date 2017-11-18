package it.red.algen.distributed;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ywilkof.sparkrestclient.DriverState;
import com.github.ywilkof.sparkrestclient.SparkRestClient;

import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.distributed.context.DistributedAlgorithmContext;

public class SparkTaskExecutor {
	private static Logger logger = LoggerFactory.getLogger(SparkTaskExecutor.class);
	

    public String runDistributed(SparkTaskConfig config, DistributedAlgorithmContext context) throws Exception {

    	// Setup configurations
    	logger.info("Task Configuration");
    	logger.info(config.toString());
    	
    	String contextAsString = ReadConfigSupport.writeJSONString(context);
    	
    	// Create client
    	logger.info("Creating client..");
    	final Map<String,String> environmentVariables = new HashMap<>();
    	environmentVariables.put("log4j.configuration",				config.log4jConfiguration);
    	environmentVariables.put("spark.eventLog.enabled",			config.historyEventsEnabled);
    	environmentVariables.put("spark.eventLog.dir",				config.historyEventsDir);
    	environmentVariables.put("spark.history.fs.logDirectory",	config.historyEventsDir);
    	final SparkRestClient sparkClient = SparkRestClient.builder()
        	.masterHost(config.masterHost)
        	.sparkVersion(config.sparkVersion)
        	.environmentVariables(environmentVariables)
        	.build();
    	logger.info("Client created on API root: "+sparkClient.getMasterApiRoot());
    	
    	// Submit job
    	logger.info("Submitting job..");
    	final String submissionId = sparkClient.prepareJobSubmit()
    		    .appName(config.appName)
    		    .appResource(config.appJar)
    		    .mainClass(config.mainClass)
    		    .appArgs(Arrays.asList(config.masterURI, contextAsString))
    		.submit();
    	logger.info("Job submitted, with id: "+submissionId);
    	
    	// Check status
    	logger.info("Checking status every minute or so..");
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
