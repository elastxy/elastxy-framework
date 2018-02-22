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
package org.elastxy.distributed.tracking.kafka;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.elastxy.core.dataprovider.DataAccessException;
import org.elastxy.core.support.JSONSupport;
import org.elastxy.distributed.context.DistributedAlgorithmContext;
import org.elastxy.distributed.stats.MultiColonyExperimentStats;
import org.elastxy.distributed.tracking.DistributedResultsCollector;


/**
 * A Kafka client for submitting and retrieving results 
 * related to a distributed execution.
 * 
 * @author red
 *
 */
public class KafkaDistributedResultsCollector implements DistributedResultsCollector {
	private static Logger logger = Logger.getLogger(KafkaDistributedResultsCollector.class);


	private Properties producerProps = new Properties();
	private Properties consumerProps = new Properties();
	
	
	@Override
	public void setup(DistributedAlgorithmContext context) {
		context.messagingProperties.forEach((k,v)->{
			String key = (String)k;
			if(key.startsWith("messaging.producer.")){
				producerProps.put(key.substring("messaging.producer.".length(), key.length()), v);
			}
			else if(key.startsWith("messaging.consumer.")){
				consumerProps.put(key.substring("messaging.consumer.".length(), key.length()), v);
			}
		});
	}



	// TODO0-2: Producer should be singleton (is thread-safe)
	@Override
	public void produceResults(String taskIdentifier, MultiColonyExperimentStats stats) {

		try (Producer<String, String> producer = new KafkaProducer<>(producerProps);) {
			String msg = JSONSupport.writeJSONString(stats, true);
			producer.send(new ProducerRecord<String, String>("elastxy-results", taskIdentifier, msg)); // TODO0-2: kafka topic per app
			producer.close(); // TODO1-2: close the kafka producer
		}
		catch(Exception ex){
			String msg = "Error while producing results to Kafka. TaskId: "+taskIdentifier+"Ex: "+ex;
			logger.error(msg, ex);
			throw new DataAccessException(msg, ex);
		}
	}


	@Override
	public MultiColonyExperimentStats consumeResults(String taskIdentifier) {

		// Create Consumer
		Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
		consumer.subscribe(Arrays.asList("elastxy-results")); // TODO0-2: kafka topic per app

		// Execute async
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		
		long timeout = 30; // polling overall timeout in seconds
		PollResultsCommand cmd = new PollResultsCommand(consumer, taskIdentifier, timeout * 1000);
		
		CompletableFuture<MultiColonyExperimentStats> future = pollForCompletion(executor, cmd, 500L);
		MultiColonyExperimentStats result = null;
		try {
			result = future.get();
		}
		catch(DataAccessException ex){
			throw ex;
		}
		catch(ExecutionException | InterruptedException ex){
			logger.warn("Generic ExecutionException from Future while polling Kafka topic. Ex: "+ex);
		}
		finally{
			consumer.close();// TODO0-2: Producer should be singleton (is thread-safe)
		}
		if(result==null){
			throw new DataAccessException("Cannot retrieve message from Kafka topic after "+timeout+" s.");
		}
		return result;
	}
	

	private static CompletableFuture<MultiColonyExperimentStats> pollForCompletion(
			ScheduledExecutorService executor, 
			PollResultsCommand cmd, 
			long frequency) {
		
	    CompletableFuture<MultiColonyExperimentStats> completionFuture = new CompletableFuture<>();
	    
	    final ScheduledFuture<?> checkFuture = executor.scheduleAtFixedRate(() -> {
    		cmd.poll();
    		if(cmd.getResults()!=null){
    			completionFuture.complete(cmd.getResults());
    		}
    		else if(cmd.getException()!=null){
    			completionFuture.completeExceptionally(cmd.getException());
    		}
    		else if(cmd.isTimedOut()){
    			completionFuture.complete(null);
    		}
	    }, 0, frequency, TimeUnit.MILLISECONDS);
	    
	    completionFuture.whenComplete((result, thrown) -> {
	        checkFuture.cancel(true);
	        executor.shutdown();
	    });
	    
	    return completionFuture;
	}
	
}
