package org.elastxy.distributed.tracking.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
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


	private Producer<String, String> producer;

	private Consumer<String, String> consumer;


	@Override
	public void init(DistributedAlgorithmContext context) {

		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.1.101:9092,192.168.1.101:9093,192.168.1.101:9094");
		props.put("acks", "all");
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// props.put("retries", 0); // exactly-once delivery
		props.put("enable.idempotence", "true"); // exactly-once delivery
		producer = new KafkaProducer<>(props);


		props = new Properties();
		props.put("bootstrap.servers", "192.168.1.101:9092,192.168.1.101:9093,192.168.1.101:9094");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(props);
	}



	// TODO0-2: Producer should be singleton (is thread-safe)
	@Override
	public void produceResults(String taskIdentifier, MultiColonyExperimentStats stats) {

		try {
			String msg = JSONSupport.writeJSONString(stats, true);
			producer.send(new ProducerRecord<String, String>("elastxy-results", taskIdentifier, msg)); // TODO0-2: kafka topic per app
			//			 producer.close(); // TODO1-2: close the kafka producer
		}
		catch(Exception ex){
			String msg = "Error while producing results to Kafka. TaskId: "+taskIdentifier+"Ex: "+ex;
			logger.error(msg, ex);
			throw new DataAccessException(msg, ex);
		}
	}


	@Override
	public MultiColonyExperimentStats consumeResults(String taskIdentifier) {
		MultiColonyExperimentStats result = null;

		consumer.subscribe(Arrays.asList("elastxy-results")); // TODO0-2: kafka topic per app

		extloop:
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					if(record.key().equals(taskIdentifier)){
						try {
							result = (MultiColonyExperimentStats)JSONSupport.readJSONString(record.value(), MultiColonyExperimentStats.class, true);
						}
						catch(Exception ex){
							String msg = "Error while consuming results from Kafka. TaskId: "+taskIdentifier+"Ex: "+ex;
							logger.error(msg, ex);
							throw new DataAccessException(msg, ex);
						}
						break extloop;
					}
				}
			}
		consumer.close();// TODO0-2: Producer should be singleton (is thread-safe)
		producer.close();// TODO0-2: Producer should be singleton (is thread-safe)
		return result;
	}

}
