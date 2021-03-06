/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.elastxy.distributed.tracking;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.elastxy.core.dataprovider.DataAccessException;
import org.elastxy.core.support.JSONSupport;
import org.elastxy.distributed.context.DistributedAlgorithmContext;
import org.elastxy.distributed.stats.MultiColonyExperimentStats;

/**
 * Utility class for writing and collecting distributed results.
 * 
 * Default implementation: a Stats object is serialized as a JSON on local Driver
 * file system, then retrieved to be passed back to client.
 * 
 * A more sophisticated implementation may use asynch messaging
 * system like Apache Kafka.
 * 
 * @author red
 *
 */
public class StandardDistributedResultsCollector implements DistributedResultsCollector {
	private static Logger logger = Logger.getLogger(StandardDistributedResultsCollector.class);
	
	
	/**
	 * Local/shared file system path where to store and retrieve results.
	 */
	private String exchangePath;
	
	
	public void setup(DistributedAlgorithmContext context){
		this.exchangePath = context.exchangePath;
	}
	
	
	public void produceResults(String taskIdentifier, MultiColonyExperimentStats stats) {
		File outputFile = new File(exchangePath, taskIdentifier+".json");
    	logger.info("Writing results to file: "+outputFile);
    	try {
    		JSONSupport.writeJSONObject(outputFile, stats);
    	}
    	catch(Exception ex){
    		String msg = "Error while writing results to: "+outputFile;
        	logger.error(msg);
    		throw new DataAccessException(msg);
    	}
    	logger.info("Results write completed.");
	}
	
	
    public MultiColonyExperimentStats consumeResults(String taskIdentifier) {
    	File inputFile = new File(exchangePath, taskIdentifier+".json");
    	logger.info("Reading results from file: "+inputFile);
    	MultiColonyExperimentStats stats = null;
    	try {
    		InputStream inputStream = new FileInputStream(inputFile);
    		stats = (MultiColonyExperimentStats)JSONSupport.readJSON(inputStream, MultiColonyExperimentStats.class, true);
    	}
    	catch(IOException ex){
    		String msg = "Error while reading results from File: "+inputFile+" Ex: "+ex;
        	logger.error(msg, ex);
    		throw new DataAccessException(msg, ex);
    	}
    	logger.info("Results read completed.");
    	return stats;
    }
    
    
}
