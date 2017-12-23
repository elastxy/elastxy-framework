package org.elastxy.distributed.tracking;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.elastxy.core.support.JSONSupport;
import org.elastxy.distributed.stats.MultiColonyExperimentStats;

/**
 * Utility class for writing and collecting distributed
 * results.
 * By now, a Stats object is serialized as a JSON on local Driver
 * file system.
 * 
 * TODOM-2: generic interface for collecting/writing results.
 * 
 * @author red
 *
 */
public class DistributedResultsCollector {
	private static Logger logger = Logger.getLogger(DistributedResultsCollector.class);
	
	
	public static void writeResults(String outputPath, String taskIdentifier, MultiColonyExperimentStats stats) throws IOException {
		File outputFile = new File(outputPath, taskIdentifier+".json");
    	logger.info("Writing results to file: "+outputFile);
    	try {
    		JSONSupport.writeJSONObject(outputFile, stats);
    	}
    	catch(Exception ex){
        	logger.error("Error while writing results to: "+outputFile);
    		throw ex;
    	}
    	logger.info("Results write completed.");
	}
	
	
    public static MultiColonyExperimentStats retrieveResults(String outputPath, String taskIdentifier) throws IOException {
    	File inputFile = new File(outputPath, taskIdentifier+".json");
    	logger.info("Reading results from file: "+inputFile);
    	MultiColonyExperimentStats stats = null;
    	InputStream inputStream = null;
    	try {
    		inputStream = new FileInputStream(inputFile);
    		stats = (MultiColonyExperimentStats)JSONSupport.readJSON(inputStream, MultiColonyExperimentStats.class, true);
    	}
    	catch(IOException ex){
        	logger.error("Error while reading results from: "+inputFile);
    		throw ex;
    	}
    	finally {
    		if(inputStream!=null) inputStream.close();
    	}
    	logger.info("Results read completed.");
    	return stats;
    }
    
    
}
