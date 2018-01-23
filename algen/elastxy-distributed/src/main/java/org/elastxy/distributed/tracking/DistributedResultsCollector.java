package org.elastxy.distributed.tracking;

import org.elastxy.distributed.context.DistributedAlgorithmContext;
import org.elastxy.distributed.stats.MultiColonyExperimentStats;

/**
 * Interface for writing and retrieving distributed execution results 
 * over nodes, related to a single execution.
 * 
 * A more sophisticated implementation may use asynch messaging
 * system like Apache Kafka.
 * 
 * @author red
 *
 */
public interface DistributedResultsCollector {

	public void setup(DistributedAlgorithmContext context);

	/**
	 * Produce execution results and puts them 
	 * into the destination, based on implementation.
	 * 
	 * @param taskIdentifier
	 * @param stats
	 */
	public void produceResults(String taskIdentifier, MultiColonyExperimentStats stats);
	
	/**
	 * Consume execution results from the source,
	 * based on implementation.
	 * 
	 * @param taskIdentifier
	 * @param stats
	 */
    public MultiColonyExperimentStats consumeResults(String taskIdentifier);
}
