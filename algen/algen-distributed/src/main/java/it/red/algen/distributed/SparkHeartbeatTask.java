package it.red.algen.distributed;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ywilkof.sparkrestclient.DriverState;
import com.github.ywilkof.sparkrestclient.SparkRestClient;

import scala.Tuple2;

@Component
public class SparkHeartbeatTask {
	private static Logger logger = LoggerFactory.getLogger(SparkHeartbeatTask.class);

	@Autowired
	private JavaSparkContext sparkContext;
	

    public String runDistributed(SparkJobConfig config) throws Exception {
    	    	
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
    		    .appArgs(Arrays.asList(config.masterURI))
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
    
	
	public String runSingle(String inputFilePath) {
//	    final StringBuffer buf = new StringBuffer();
//	    final List<String> list = new ArrayList<String>();

	    JavaPairRDD<String, Integer> wordCount = sparkContext.textFile(inputFilePath)
	        .flatMap(text -> Arrays.asList(text.split(" ")).iterator())
	        .mapToPair(word -> new Tuple2<>(word, 1))
	        .reduceByKey((a, b) -> a + b);
	    
//	        .foreach(res -> logger.info(String.format("Word [%s] count [%d].%n", res._1(), res._2)));
//	        .foreach(res -> write(buf, list, String.format("Word [%s] count [%d].%n", res._1(), res._2)));
	    
//	    String result = wordCount.toDebugString();
	    
//	    wordCount.collect().stream(tuple -> write(buf, list, String.format("Word [%s] count [%d].%n", tuple._1, tuple_2)));
	    Stream<Tuple2<String,Integer>> s1 = wordCount.collect().stream();
	    Stream<String> s2 = s1.map(tuple -> String.format("Word [%s] count [%d].%n", tuple._1(), tuple._2));
//	    s2.forEach(SparkHeartbeatTask::write);
//	    s2.forEach(action);
	    
//	    String res = buf.toString();
//	    logger.info(res);
//	    return "BUF=\n"+res+"\nLIST=\n"+list.toString();
	    
	    return Arrays.toString(s2.toArray());
	}
	

    public String runConcurrent(String textPath1, String textPath2) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // Start thread 1
        Future<Long> future1 = executorService.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                JavaRDD<String> file1 = sparkContext.textFile(textPath1);
                return file1.count();
            }
        });
        // Start thread 2
        Future<Long> future2 = executorService.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                JavaRDD<String> file2 = sparkContext.textFile(textPath2);
                return file2.count();
            }
        });
        // Wait thread 1
        logger.info("File1:"+future1.get());
        // Wait thread 2
        logger.info("File2:"+future2.get());
        
        return "Counts: "+future1.get()+" & "+future2.get();
    }
    
    
    
//	private static void write(String buf){
//		System.out.println(buf);;
//	}

//	private static StringBuffer write(StringBuffer buf, List<String> list, String msg){
//		logger.info(msg);
//		list.add(msg);
//		return buf.append(msg);
//	}
}
