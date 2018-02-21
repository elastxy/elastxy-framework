package org.elastxy.distributed.tracking.kafka;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.log4j.Logger;
import org.elastxy.core.dataprovider.DataAccessException;
import org.elastxy.core.support.JSONSupport;
import org.elastxy.distributed.stats.MultiColonyExperimentStats;

public class PollResultsCommand {
	private static Logger logger = Logger.getLogger(PollResultsCommand.class);

	// Input
	private Consumer<String, String> consumer;
	private String taskIdentifier;
	private long timeout;

	// Output
	private MultiColonyExperimentStats result;
	private DataAccessException exception;
	private boolean timedOut;
    
    public PollResultsCommand(Consumer<String, String> consumer, String taskIdentifier, long timeoutMs) {
    	this.consumer = consumer;
    	this.taskIdentifier = taskIdentifier;
    	timeout = System.currentTimeMillis()+timeoutMs;
	}
    
    public MultiColonyExperimentStats getResults(){
    	return result;
    }
    
    public DataAccessException getException(){
    	return exception;
    }

    public boolean isTimedOut(){
    	return timedOut;
    }
    
    public void poll(){
    	if(System.currentTimeMillis() > timeout){
    		timedOut = true;
    		return;
    	}
    	try {
    		ConsumerRecords<String, String> records = consumer.poll(200);
    		for (ConsumerRecord<String, String> record : records) {
    			if(record.key()==null){
    				logger.warn("Wrong message on topic: no key is specified. Msg: "+record);
    				continue;
    			}
    			if(record.key().equals(taskIdentifier)){
					result = (MultiColonyExperimentStats)JSONSupport.readJSONString(record.value(), MultiColonyExperimentStats.class, true);
					break;
    			}
    		}
    	}
    	catch(Exception ex){
    		String msg = "Error while consuming results from Kafka. TaskId: "+taskIdentifier+"Ex: "+ex;
    		logger.error(msg, ex);
    		throw new DataAccessException(msg, ex);
    	}
    }
}
